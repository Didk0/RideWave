package com.ride.wave.driver.service.producer;

import static com.ride.wave.shared.constants.KafkaConstants.DRIVER_ASSIGNMENTS_TOPIC;

import com.ride.wave.shared.dto.event.DriverAssignedEvent;
import com.ride.wave.shared.kafka.producer.BaseKafkaProducer;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class DriverAssignedProducer extends BaseKafkaProducer<DriverAssignedEvent> {

  protected DriverAssignedProducer(final KafkaTemplate<String, DriverAssignedEvent> kafkaTemplate) {

    super(kafkaTemplate);
  }

  public void sendDriverAssignedEvent(final DriverAssignedEvent event) {

    super.sendEvent(DRIVER_ASSIGNMENTS_TOPIC, event.tripId(), event);
  }
}
