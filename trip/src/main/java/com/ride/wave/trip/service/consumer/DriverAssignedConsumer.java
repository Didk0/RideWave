package com.ride.wave.trip.service.consumer;

import static com.ride.wave.shared.constants.KafkaConstants.DRIVER_ASSIGNMENTS_TOPIC;
import static com.ride.wave.shared.constants.KafkaConstants.TRIP_SERVICE_GROUP_ID;

import com.ride.wave.shared.dto.event.DriverAssignedEvent;
import com.ride.wave.shared.kafka.consumer.BaseKafkaConsumer;
import com.ride.wave.trip.service.TripService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DriverAssignedConsumer implements BaseKafkaConsumer<DriverAssignedEvent> {

  private final TripService tripService;

  @Override
  @KafkaListener(topics = DRIVER_ASSIGNMENTS_TOPIC, groupId = TRIP_SERVICE_GROUP_ID)
  public void receiveEvent(final DriverAssignedEvent event) {

    log.info("Received Driver Assigned event: {}", event);

    tripService.updateTripAfterDriverAssignment(event);
  }
}
