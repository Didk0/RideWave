package com.ride.wave.trip.service.impl;

import static com.ride.wave.shared.constants.KafkaConstants.DRIVER_ASSIGNMENT_REQUESTS_TOPIC;

import com.ride.wave.shared.dto.event.DriverAssignmentRequestedEvent;
import com.ride.wave.trip.service.DriverAssignmentRequestProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DriverAssignmentRequestProducerImpl implements DriverAssignmentRequestProducer {

  private final KafkaTemplate<String, DriverAssignmentRequestedEvent> kafkaTemplate;

  @Override
  public void sendDriverAssignmentRequestedEvent(final DriverAssignmentRequestedEvent event) {

    kafkaTemplate.send(DRIVER_ASSIGNMENT_REQUESTS_TOPIC, event.tripId(), event);

    log.info("Sent Driver Assignment Request: {}", event);
  }
}
