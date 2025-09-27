package com.ride.wave.rider.service.producer;

import static com.ride.wave.shared.constants.KafkaConstants.RIDE_REQUESTS_TOPIC;

import com.ride.wave.shared.dto.event.RideRequestedEvent;
import com.ride.wave.shared.kafka.producer.BaseKafkaProducer;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class RideRequestProducer extends BaseKafkaProducer<RideRequestedEvent> {

  protected RideRequestProducer(final KafkaTemplate<String, RideRequestedEvent> kafkaTemplate) {

    super(kafkaTemplate);
  }

  public void sendRideRequestedEvent(final RideRequestedEvent event) {

    super.sendEvent(RIDE_REQUESTS_TOPIC, event.requestId(), event);
  }
}
