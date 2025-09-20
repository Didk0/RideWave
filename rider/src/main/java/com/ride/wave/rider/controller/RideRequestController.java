package com.ride.wave.rider.controller;

import com.ride.wave.rider.dto.request.CreateRideRequest;
import com.ride.wave.rider.dto.response.RideRequestDto;
import com.ride.wave.rider.service.RideRequestService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ride-requests")
@RequiredArgsConstructor
public class RideRequestController {

  private final RideRequestService rideRequestService;

  @PostMapping
  public ResponseEntity<RideRequestDto> createRideRequest(
      @RequestParam final Long riderId, @RequestBody final CreateRideRequest request) {

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(rideRequestService.createRideRequest(riderId, request));
  }

  @GetMapping("/{id}")
  public ResponseEntity<RideRequestDto> getRideRequest(@PathVariable final Long id) {

    return ResponseEntity.ok(rideRequestService.getRideRequest(id));
  }

  @GetMapping
  public ResponseEntity<List<RideRequestDto>> getRideRequests(
      @RequestParam(required = false) final Long riderId) {

    return ResponseEntity.ok(rideRequestService.getRideRequests(riderId));
  }
}
