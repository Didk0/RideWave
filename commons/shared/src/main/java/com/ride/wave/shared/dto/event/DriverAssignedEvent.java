package com.ride.wave.shared.dto.event;

import java.time.Instant;
import lombok.Builder;

@Builder
public record DriverAssignedEvent(
    String tripId, String driverId, String driverName, String vehicle, Instant startedAt) {}
