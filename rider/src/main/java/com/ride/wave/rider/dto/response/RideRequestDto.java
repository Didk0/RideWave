package com.ride.wave.rider.dto.response;

import com.ride.wave.rider.enums.RideStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RideRequestDto {

  private Long id;
  private Long riderId;
  private String pickup;
  private String destination;
  private RideStatus status;
}
