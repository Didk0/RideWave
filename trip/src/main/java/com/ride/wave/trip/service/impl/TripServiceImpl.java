package com.ride.wave.trip.service.impl;

import com.ride.wave.shared.dto.event.DriverAssignedEvent;
import com.ride.wave.shared.dto.event.RideRequestedEvent;
import com.ride.wave.trip.dto.response.TripDto;
import com.ride.wave.trip.entity.Trip;
import com.ride.wave.trip.enums.TripStatus;
import com.ride.wave.trip.repository.TripRepository;
import com.ride.wave.trip.service.TripService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TripServiceImpl implements TripService {

  private final TripRepository tripRepository;
  private final ModelMapper modelMapper;

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
  public TripDto getTrip(final String id) {

    final Trip trip =
        tripRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("Trip with id " + id + " not found."));

    return modelMapper.map(trip, TripDto.class);
  }

  @Override
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
  public void deleteTrip(final String id) {

    tripRepository.deleteById(id);
  }

  @Override
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
}
