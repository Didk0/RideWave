package com.ride.wave.trip.service;

import com.ride.wave.shared.dto.event.DriverAssignmentRequestedEvent;

public interface DriverAssignmentRequestProducer {

  void sendDriverAssignmentRequestedEvent(DriverAssignmentRequestedEvent event);
}
