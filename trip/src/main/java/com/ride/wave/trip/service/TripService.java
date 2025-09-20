package com.ride.wave.trip.service;

import com.ride.wave.shared.dto.event.DriverAssignedEvent;
import com.ride.wave.shared.dto.event.RideRequestedEvent;
import com.ride.wave.trip.dto.response.TripDto;
import java.util.List;

public interface TripService {

  TripDto createTrip(RideRequestedEvent event);

  TripDto getTrip(String id);

  List<TripDto> getTrips(Long riderId, Long driverId);

  void deleteTrip(String id);

  void updateTripAfterDriverAssignment(DriverAssignedEvent event);
}
