package com.ride.wave.rider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.ride.wave"})
public class RiderApplication {

  public static void main(final String[] args) {

    SpringApplication.run(RiderApplication.class, args);
  }
}
