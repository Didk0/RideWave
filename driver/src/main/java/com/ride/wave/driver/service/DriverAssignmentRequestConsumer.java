package com.ride.wave.driver.service;

import com.ride.wave.shared.dto.event.DriverAssignmentRequestedEvent;

public interface DriverAssignmentRequestConsumer {

  void receiveDriverAssignmentRequestedEvent(DriverAssignmentRequestedEvent event);
}
