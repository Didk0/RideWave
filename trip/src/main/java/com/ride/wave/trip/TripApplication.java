package com.ride.wave.trip;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.ride.wave"})
public class TripApplication {

  public static void main(final String[] args) {
    SpringApplication.run(TripApplication.class, args);
  }
}
