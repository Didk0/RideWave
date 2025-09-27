package com.ride.wave.trip.service.impl;

import static com.ride.wave.trip.constants.Constants.CENTS_PER_SECOND_RATE;

import com.ride.wave.shared.dto.event.DriverAssignedEvent;
import com.ride.wave.shared.dto.event.DriverStatusUpdateEvent;
import com.ride.wave.shared.dto.event.RideRequestedEvent;
import com.ride.wave.shared.dto.event.RideStatusUpdateEvent;
import com.ride.wave.shared.enums.DriverStatus;
import com.ride.wave.shared.enums.RideStatus;
import com.ride.wave.trip.dto.response.TripDto;
import com.ride.wave.trip.entity.Trip;
import com.ride.wave.trip.enums.TripStatus;
import com.ride.wave.trip.repository.TripRepository;
import com.ride.wave.trip.service.TripService;
import com.ride.wave.trip.service.producer.DriverStatusUpdateProducer;
import com.ride.wave.trip.service.producer.RideStatusUpdateProducer;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TripServiceImpl implements TripService {

  private final TripRepository tripRepository;
  private final ModelMapper modelMapper;
  private final DriverStatusUpdateProducer driverStatusUpdateProducer;
  private final RideStatusUpdateProducer rideStatusUpdateProducer;

  @Override
  public TripDto createTrip(final RideRequestedEvent event) {

    final Trip trip =
        Trip.builder()
            .rideRequestId(Long.valueOf(event.requestId()))
            .riderId(Long.valueOf(event.riderId()))
            .riderName(event.riderName())
            .riderEmail(event.riderEmail())
            .pickup(event.pickupLocation())
            .destination(event.destinationLocation())
            .status(TripStatus.PENDING)
            .build();

    tripRepository.save(trip);

    return modelMapper.map(trip, TripDto.class);
  }

  @Override
  @Transactional(readOnly = true)
  public TripDto getTrip(final String id) {

    final Trip trip =
        tripRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("Trip with id " + id + " not found."));

    return modelMapper.map(trip, TripDto.class);
  }

  @Override
  @Transactional(readOnly = true)
  public List<TripDto> getTrips(final Long riderId, final Long driverId) {

    final List<Trip> trips;

    if (riderId != null && driverId != null) {
      trips = tripRepository.findByRiderIdAndDriverId(riderId, driverId);
    } else {
      if (riderId != null) {
        trips = tripRepository.findByRiderId(riderId);
      } else if (driverId != null) {
        trips = tripRepository.findByDriverId(driverId);
      } else {
        trips = tripRepository.findAll();
      }
    }

    return trips.stream().map(trip -> modelMapper.map(trip, TripDto.class)).toList();
  }

  @Override
  @Transactional
  public void deleteTrip(final String id) {

    tripRepository.deleteById(id);
  }

  @Override
  @Transactional
  public void updateTripAfterDriverAssignment(final DriverAssignedEvent event) {

    final Trip tripToUpdate =
        tripRepository
            .findById(event.tripId())
            .orElseThrow(
                () ->
                    new ResourceNotFoundException("Trip with id " + event.tripId() + " not found"));

    tripToUpdate.setDriverId(Long.valueOf(event.driverId()));
    tripToUpdate.setDriverName(event.driverName());
    tripToUpdate.setVehicle(event.vehicle());

    tripRepository.save(tripToUpdate);
  }

  @Override
  @Transactional
  public void startTrip(final String tripId) {

    final Trip trip =
        tripRepository
            .findById(tripId)
            .orElseThrow(
                () -> new ResourceNotFoundException("Trip with id " + tripId + " not found"));

    trip.setStatus(TripStatus.STARTED);
    trip.setStartedAt(Instant.now());

    tripRepository.save(trip);

    final DriverStatusUpdateEvent driverEvent =
        DriverStatusUpdateEvent.builder()
            .driverId(trip.getDriverId())
            .status(DriverStatus.BUSY)
            .build();

    final RideStatusUpdateEvent rideEvent =
        RideStatusUpdateEvent.builder()
            .rideRequestId(trip.getRideRequestId())
            .status(RideStatus.ASSIGNED)
            .build();

    driverStatusUpdateProducer.sendDriverStatusUpdate(driverEvent);
    rideStatusUpdateProducer.sendRideStatusUpdate(rideEvent);
  }

  @Override
  public void rejectTrip(final String tripId) {

    final Trip trip =
        tripRepository
            .findById(tripId)
            .orElseThrow(
                () -> new ResourceNotFoundException("Trip with id " + tripId + " not found"));

    final Long driverId = trip.getDriverId();

    trip.setDriverId(null);
    trip.setDriverName(null);
    trip.setVehicle(null);

    tripRepository.save(trip);

    final DriverStatusUpdateEvent driverEvent =
        DriverStatusUpdateEvent.builder().driverId(driverId).status(DriverStatus.REJECTED).build();

    driverStatusUpdateProducer.sendDriverStatusUpdate(driverEvent);
  }

  @Override
  public void completeTrip(final String tripId) {

    final Trip trip =
        tripRepository
            .findById(tripId)
            .orElseThrow(
                () -> new ResourceNotFoundException("Trip with id " + tripId + " not found"));

    trip.setStatus(TripStatus.COMPLETED);
    trip.setCompletedAt(Instant.now());

    final BigDecimal calculatedFare =
        BigDecimal.valueOf(CENTS_PER_SECOND_RATE)
            .multiply(
                BigDecimal.valueOf(
                    Duration.between(trip.getStartedAt(), trip.getCompletedAt()).getSeconds()));
    trip.setFare(calculatedFare);

    tripRepository.save(trip);

    final DriverStatusUpdateEvent driverEvent =
        DriverStatusUpdateEvent.builder()
            .driverId(trip.getDriverId())
            .status(DriverStatus.AVAILABLE)
            .build();

    final RideStatusUpdateEvent rideEvent =
        RideStatusUpdateEvent.builder()
            .rideRequestId(trip.getRideRequestId())
            .status(RideStatus.COMPLETED)
            .build();

    driverStatusUpdateProducer.sendDriverStatusUpdate(driverEvent);
    rideStatusUpdateProducer.sendRideStatusUpdate(rideEvent);
  }
}
