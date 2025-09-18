package com.ride.wave.gateway;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fallback")
public class FallbackController {

  @RequestMapping("/riders")
  public ResponseEntity<String> riderFallback() {

    return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
        .body("Rider Service is currently unavailable. Please try again later.");
  }

  @RequestMapping("/drivers")
  public ResponseEntity<String> driverFallback() {

    return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
        .body("Driver Service is currently unavailable. Please try again later.");
  }

  @RequestMapping("/trips")
  public ResponseEntity<String> tripFallback() {

    return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
        .body("Trip Service is currently unavailable. Please try again later.");
  }
}
