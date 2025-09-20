package com.ride.wave.trip.service;

import com.ride.wave.shared.dto.event.RideRequestedEvent;

public interface RideRequestConsumer {

  void receiveRideRequestedEvent(RideRequestedEvent event);
}
