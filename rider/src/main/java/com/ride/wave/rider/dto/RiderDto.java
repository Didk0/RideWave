package com.ride.wave.rider.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RiderDto {

  private Long id;
  private String name;
  private String email;
  private String phone;
}
