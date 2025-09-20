package com.ride.wave.shared.dto.event;

import lombok.Builder;

@Builder
public record DriverAssignmentRequestedEvent(
    String tripId, String pickupLocation, String destinationLocation) {}
