package com.ride.wave.trip.service.producer;

import static com.ride.wave.shared.constants.KafkaConstants.DRIVER_STATUS_UPDATE_TOPIC;

import com.ride.wave.shared.dto.event.DriverStatusUpdateEvent;
import com.ride.wave.shared.kafka.producer.BaseKafkaProducer;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class DriverStatusUpdateProducer extends BaseKafkaProducer<DriverStatusUpdateEvent> {

  protected DriverStatusUpdateProducer(
      final KafkaTemplate<String, DriverStatusUpdateEvent> kafkaTemplate) {

    super(kafkaTemplate);
  }

  public void sendDriverStatusUpdate(final DriverStatusUpdateEvent event) {

    super.sendEvent(DRIVER_STATUS_UPDATE_TOPIC, String.valueOf(event.driverId()), event);
  }
}
