package com.ride.wave.driver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.ride.wave"})
public class DriverApplication {

  public static void main(final String[] args) {
    SpringApplication.run(DriverApplication.class, args);
  }
}
