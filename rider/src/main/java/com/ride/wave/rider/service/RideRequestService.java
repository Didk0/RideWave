package com.ride.wave.rider.service;

import com.ride.wave.rider.dto.request.CreateRideRequest;
import com.ride.wave.rider.dto.response.RideRequestDto;
import com.ride.wave.shared.dto.event.RideStatusUpdateEvent;
import java.util.List;

public interface RideRequestService {

  RideRequestDto createRideRequest(Long riderId, CreateRideRequest request);

  RideRequestDto getRideRequest(Long id);

  List<RideRequestDto> getRideRequests(Long riderId);

  void updateRiderStatus(RideStatusUpdateEvent event);
}
