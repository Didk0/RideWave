package com.ride.wave.trip.controller;

import com.ride.wave.trip.dto.response.TripDto;
import com.ride.wave.trip.service.TripService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/trips")
@RequiredArgsConstructor
public class TripController {

  private final TripService tripService;

  // Used for testing/debugging
  //  @PostMapping
  //  public ResponseEntity<TripDto> createTrip(@RequestBody final RideRequestedEvent event) {
  //
  //    return ResponseEntity.status(HttpStatus.CREATED).body(tripService.createTrip(event));
  //  }

  @GetMapping("/{id}")
  public ResponseEntity<TripDto> getTrip(@PathVariable final String id) {

    return ResponseEntity.ok(tripService.getTrip(id));
  }

  @GetMapping
  public ResponseEntity<List<TripDto>> getTrips(
      @RequestParam(required = false) final Long riderId,
      @RequestParam(required = false) final Long driverId) {

    return ResponseEntity.ok(tripService.getTrips(riderId, driverId));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteTrip(@PathVariable final String id) {

    tripService.deleteTrip(id);

    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
