package com.ride.wave.trip.service.impl;

import com.ride.wave.trip.dto.TripDto;
import com.ride.wave.trip.entity.Trip;
import com.ride.wave.trip.enums.TripStatus;
import com.ride.wave.trip.payload.CreateTripRequest;
import com.ride.wave.trip.repository.TripRepository;
import com.ride.wave.trip.service.TripService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TripServiceImpl implements TripService {

  private final TripRepository tripRepository;
  private final ModelMapper modelMapper;

  @Override
  public TripDto createTrip(final CreateTripRequest request) {

    final Trip trip =
        Trip.builder()
            .riderId(request.riderId())
            .pickup(request.pickup())
            .destination(request.destination())
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

    if (riderId != null) {
      trips = tripRepository.findByRiderId(riderId);
    } else if (driverId != null) {
      trips = tripRepository.findByDriverId(driverId);
    } else {
      trips = tripRepository.findAll();
    }

    return trips.stream().map(trip -> modelMapper.map(trip, TripDto.class)).toList();
  }

  @Override
  public void deleteTrip(final String id) {

    tripRepository.deleteById(id);
  }
}
