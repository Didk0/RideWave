package com.ride.wave.trip.dto;

import com.ride.wave.trip.enums.TripStatus;
import java.math.BigDecimal;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TripDto {

  private String id;

  private Long riderId;
  private String riderName;
  private String riderEmail;

  private Long driverId;
  private String driverName;
  private String vehicle;

  private String pickup;
  private String destination;

  private TripStatus status;

  private Instant startedAt;
  private Instant completedAt;
  private BigDecimal fare;
}
