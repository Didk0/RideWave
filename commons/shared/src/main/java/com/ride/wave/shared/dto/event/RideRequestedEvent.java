package com.ride.wave.shared.dto.event;

import java.time.Instant;
import lombok.Builder;

@Builder
public record RideRequestedEvent(
    String requestId,
    String riderId,
    String riderName,
    String riderEmail,
    String pickupLocation,
    String destinationLocation,
    Instant requestedAt) {}
