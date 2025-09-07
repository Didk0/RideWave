package com.ride.wave.trip.trip;

import com.ride.wave.trip.TripStatus;
import java.math.BigDecimal;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "trips")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Trip {

  @Id private String id;

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
