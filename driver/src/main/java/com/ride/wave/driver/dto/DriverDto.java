package com.ride.wave.driver.dto;

import com.ride.wave.driver.enums.DriverStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DriverDto {

  private Long id;
  private String name;
  private String vehicle;
  private DriverStatus status;
}
