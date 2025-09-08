package com.ride.wave.rider.service.impl;

import com.ride.wave.rider.dto.RideRequestDto;
import com.ride.wave.rider.entity.RideRequest;
import com.ride.wave.rider.entity.Rider;
import com.ride.wave.rider.enums.RideStatus;
import com.ride.wave.rider.payload.CreateRideRequest;
import com.ride.wave.rider.repository.RideRequestRepository;
import com.ride.wave.rider.repository.RiderRepository;
import com.ride.wave.rider.service.RideRequestService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RideRequestServiceImpl implements RideRequestService {

  private final RideRequestRepository rideRequestRepository;
  private final RiderRepository riderRepository;
  private final ModelMapper modelMapper;

  @Override
  public RideRequestDto createRideRequest(final Long riderId, final CreateRideRequest request) {

    final Rider rider =
        riderRepository
            .findById(riderId)
            .orElseThrow(() -> new RuntimeException("Rider with id " + riderId + " not found."));

    final RideRequest rideRequest =
        RideRequest.builder()
            .rider(rider)
            .pickup(request.pickup())
            .destination(request.destination())
            .status(RideStatus.REQUESTED)
            .build();

    rideRequestRepository.save(rideRequest);

    final RideRequestDto rideRequestDto = modelMapper.map(rideRequest, RideRequestDto.class);
    rideRequestDto.setRiderId(riderId);
    return rideRequestDto;
  }

  @Override
  public RideRequestDto getRideRequest(final Long id) {

    final RideRequest rideRequest =
        rideRequestRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("Ride request with id " + id + " not found."));

    final RideRequestDto rideRequestDto = modelMapper.map(rideRequest, RideRequestDto.class);

    rideRequestDto.setRiderId(rideRequest.getRider().getId());

    return rideRequestDto;
  }

  @Override
  public List<RideRequestDto> getRideRequests(final Long riderId) {

    final List<RideRequest> requests =
        riderId != null
            ? rideRequestRepository.findByRiderId(riderId)
            : rideRequestRepository.findAll();

    return requests.stream()
        .map(rideRequest -> modelMapper.map(rideRequest, RideRequestDto.class))
        .toList();
  }
}
