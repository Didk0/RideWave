package com.ride.wave.rider.service;

import com.ride.wave.rider.dto.request.CreateRiderRequest;
import com.ride.wave.rider.dto.response.RiderDto;
import java.util.List;

public interface RiderService {

  RiderDto createRider(CreateRiderRequest request);

  RiderDto getRider(Long id);

  List<RiderDto> getAllRiders();

  RiderDto updateRider(Long id, CreateRiderRequest request);

  void deleteRider(Long id);
}
