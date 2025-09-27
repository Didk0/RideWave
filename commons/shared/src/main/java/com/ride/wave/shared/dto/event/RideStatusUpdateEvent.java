package com.ride.wave.shared.dto.event;

import com.ride.wave.shared.enums.RideStatus;
import lombok.Builder;

@Builder
public record RideStatusUpdateEvent(Long rideRequestId, RideStatus status) {}
