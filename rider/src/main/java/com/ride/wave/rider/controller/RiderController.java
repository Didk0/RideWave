package com.ride.wave.rider.controller;

import com.ride.wave.rider.dto.RiderDto;
import com.ride.wave.rider.payload.CreateRiderRequest;
import com.ride.wave.rider.service.RiderService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/riders")
@RequiredArgsConstructor
public class RiderController {

  private final RiderService riderService;

  @PostMapping
  public ResponseEntity<RiderDto> createRider(@RequestBody final CreateRiderRequest request) {

    return ResponseEntity.status(HttpStatus.CREATED).body(riderService.createRider(request));
  }

  @GetMapping("/{id}")
  public ResponseEntity<RiderDto> getRider(@PathVariable final Long id) {

    return ResponseEntity.ok(riderService.getRider(id));
  }

  @GetMapping
  public ResponseEntity<List<RiderDto>> getAllRiders() {

    return ResponseEntity.ok(riderService.getAllRiders());
  }

  @PutMapping("/{id}")
  public ResponseEntity<RiderDto> updateRider(
      @PathVariable final Long id, @RequestBody final CreateRiderRequest request) {

    return ResponseEntity.ok(riderService.updateRider(id, request));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteRider(@PathVariable final Long id) {

    riderService.deleteRider(id);

    return ResponseEntity.noContent().build();
  }
}
