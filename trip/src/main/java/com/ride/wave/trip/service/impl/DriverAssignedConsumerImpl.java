package com.ride.wave.trip.service.impl;

import static com.ride.wave.shared.constants.KafkaConstants.DRIVER_ASSIGNMENTS_TOPIC;
import static com.ride.wave.shared.constants.KafkaConstants.TRIP_SERVICE_GROUP_ID;

import com.ride.wave.shared.dto.event.DriverAssignedEvent;
import com.ride.wave.trip.service.DriverAssignedConsumer;
import com.ride.wave.trip.service.TripService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DriverAssignedConsumerImpl implements DriverAssignedConsumer {

  private final TripService tripService;

  @Override
  @KafkaListener(topics = DRIVER_ASSIGNMENTS_TOPIC, groupId = TRIP_SERVICE_GROUP_ID)
  public void receiveDriverAssignedEvent(final DriverAssignedEvent event) {

    log.info("Received Driver Assigned event: {}", event);

    tripService.updateTripAfterDriverAssignment(event);
  }
}
