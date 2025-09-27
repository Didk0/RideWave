package com.ride.wave.trip.service.producer;

import static com.ride.wave.shared.constants.KafkaConstants.DRIVER_ASSIGNMENT_REQUESTS_TOPIC;

import com.ride.wave.shared.dto.event.DriverAssignmentRequestedEvent;
import com.ride.wave.shared.kafka.producer.BaseKafkaProducer;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class DriverAssignmentRequestProducer
    extends BaseKafkaProducer<DriverAssignmentRequestedEvent> {

  protected DriverAssignmentRequestProducer(
      final KafkaTemplate<String, DriverAssignmentRequestedEvent> kafkaTemplate) {

    super(kafkaTemplate);
  }

  public void sendDriverAssignmentRequest(final DriverAssignmentRequestedEvent event) {

    super.sendEvent(DRIVER_ASSIGNMENT_REQUESTS_TOPIC, event.tripId(), event);
  }
}
