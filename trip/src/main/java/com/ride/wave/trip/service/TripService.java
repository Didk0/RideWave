package com.ride.wave.trip.service;

import com.ride.wave.trip.dto.TripDto;
import com.ride.wave.trip.payload.CreateTripRequest;
import java.util.List;

public interface TripService {

  TripDto createTrip(CreateTripRequest request);

  TripDto getTrip(String id);

  List<TripDto> getTrips(Long riderId, Long driverId);

  void deleteTrip(String id);
}
