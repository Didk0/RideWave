package com.ride.wave.rider.service;

import com.ride.wave.rider.dto.RideRequestDto;
import com.ride.wave.rider.payload.CreateRideRequest;
import java.util.List;

public interface RideRequestService {

  RideRequestDto createRideRequest(Long riderId, CreateRideRequest request);

  RideRequestDto getRideRequest(Long id);

  List<RideRequestDto> getRideRequests(Long riderId);
}
