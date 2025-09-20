package com.ride.wave.trip.service;

import com.ride.wave.shared.dto.event.DriverAssignedEvent;

public interface DriverAssignedConsumer {

  void receiveDriverAssignedEvent(DriverAssignedEvent event);
}
