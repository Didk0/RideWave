package com.ride.wave.driver.service;

import com.ride.wave.shared.dto.event.DriverAssignedEvent;

public interface DriverAssignedProducer {

  void sendDriverAssignedEvent(DriverAssignedEvent event);
}
