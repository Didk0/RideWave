package com.ride.wave.shared.dto.event;

import com.ride.wave.shared.enums.DriverStatus;
import lombok.Builder;

@Builder
public record DriverStatusUpdateEvent(Long driverId, DriverStatus status) {}
