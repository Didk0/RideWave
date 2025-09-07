package com.ride.wave.rider.entity;

import com.ride.wave.rider.enums.RideStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ride_requests")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RideRequest {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "rider_id", nullable = false)
  private Rider rider;

  private String pickup;
  private String destination;

  @Enumerated(EnumType.STRING)
  private RideStatus status;

  private Instant createdAt;
}
