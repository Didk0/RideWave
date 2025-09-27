package com.ride.wave.rider.service.impl;

import com.ride.wave.rider.dto.request.CreateRideRequest;
import com.ride.wave.rider.dto.response.RideRequestDto;
import com.ride.wave.rider.entity.RideRequest;
import com.ride.wave.rider.entity.Rider;
import com.ride.wave.rider.repository.RideRequestRepository;
import com.ride.wave.rider.repository.RiderRepository;
import com.ride.wave.rider.service.RideRequestService;
import com.ride.wave.rider.service.producer.RideRequestProducer;
import com.ride.wave.shared.dto.event.RideRequestedEvent;
import com.ride.wave.shared.dto.event.RideStatusUpdateEvent;
import com.ride.wave.shared.enums.RideStatus;
import jakarta.transaction.Transactional;
import java.time.Instant;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RideRequestServiceImpl implements RideRequestService {

  private final RideRequestRepository rideRequestRepository;
  private final RiderRepository riderRepository;
  private final ModelMapper modelMapper;
  private final RideRequestProducer rideRequestProducer;

  @Override
  @Transactional
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

    rideRequestProducer.sendRideRequestedEvent(
        RideRequestedEvent.builder()
            .requestId(String.valueOf(rideRequest.getId()))
            .riderId(String.valueOf(riderId))
            .riderName(rider.getName())
            .riderEmail(rider.getEmail())
            .pickupLocation(request.pickup())
            .destinationLocation(request.destination())
            .requestedAt(Instant.now())
            .build());

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

  @Override
  @Transactional
  public void updateRiderStatus(final RideStatusUpdateEvent event) {

    final RideRequest rideRequest =
        rideRequestRepository
            .findById(event.rideRequestId())
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        "RideRequest with id " + event.rideRequestId() + " not found"));

    rideRequest.setStatus(event.status());

    rideRequestRepository.save(rideRequest);
  }
}
