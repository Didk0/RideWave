package com.ride.wave.rider.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "riders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rider {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private String email;
  private String phone;

  @Builder.Default private Instant createdAt = Instant.now();

  @OneToMany(mappedBy = "rider", cascade = CascadeType.ALL, orphanRemoval = true)
  @Builder.Default
  private List<RideRequest> rideRequests = new ArrayList<>();
}
