package com.ride.wave.driver.service.impl;

import static com.ride.wave.shared.constants.KafkaConstants.DRIVER_ASSIGNMENTS_TOPIC;

import com.ride.wave.driver.service.DriverAssignedProducer;
import com.ride.wave.shared.dto.event.DriverAssignedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DriverAssignedProducerImpl implements DriverAssignedProducer {

  private final KafkaTemplate<String, DriverAssignedEvent> kafkaTemplate;

  @Override
  public void sendDriverAssignedEvent(final DriverAssignedEvent event) {

    kafkaTemplate.send(DRIVER_ASSIGNMENTS_TOPIC, event.tripId(), event);

    log.info("Sent Driver Assigned event: {}", event);
  }
}
