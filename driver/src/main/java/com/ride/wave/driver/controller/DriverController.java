package com.ride.wave.driver.controller;

import com.ride.wave.driver.dto.DriverDto;
import com.ride.wave.driver.payload.CreateDriverRequest;
import com.ride.wave.driver.service.DriverService;
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
@RequestMapping("/api/drivers")
@RequiredArgsConstructor
public class DriverController {

  private final DriverService driverService;

  @PostMapping
  public ResponseEntity<DriverDto> createDriver(@RequestBody final CreateDriverRequest request) {

    return ResponseEntity.status(HttpStatus.CREATED).body(driverService.createDriver(request));
  }

  @GetMapping("/{id}")
  public ResponseEntity<DriverDto> getDriver(@PathVariable final Long id) {

    return ResponseEntity.ok(driverService.getDriver(id));
  }

  @GetMapping
  public ResponseEntity<List<DriverDto>> getAllDrivers() {

    return ResponseEntity.ok(driverService.getAllDrivers());
  }

  @PutMapping("/{id}")
  public ResponseEntity<DriverDto> updateDriver(
      @PathVariable final Long id, @RequestBody final CreateDriverRequest request) {

    return ResponseEntity.ok(driverService.updateDriver(id, request));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteDriver(@PathVariable final Long id) {

    driverService.deleteDriver(id);

    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
