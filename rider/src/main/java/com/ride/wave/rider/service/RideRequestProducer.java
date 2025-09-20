package com.ride.wave.rider.service;

import com.ride.wave.shared.dto.event.RideRequestedEvent;

public interface RideRequestProducer {

  void sendRideRequestedEvent(RideRequestedEvent event);
}
