package com.ride.wave.trip.service.producer;

import static com.ride.wave.shared.constants.KafkaConstants.RIDE_STATUS_UPDATE_TOPIC;

import com.ride.wave.shared.dto.event.RideStatusUpdateEvent;
import com.ride.wave.shared.kafka.producer.BaseKafkaProducer;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class RideStatusUpdateProducer extends BaseKafkaProducer<RideStatusUpdateEvent> {

  protected RideStatusUpdateProducer(
      final KafkaTemplate<String, RideStatusUpdateEvent> kafkaTemplate) {

    super(kafkaTemplate);
  }

  public void sendRideStatusUpdate(final RideStatusUpdateEvent event) {

    super.sendEvent(RIDE_STATUS_UPDATE_TOPIC, String.valueOf(event.rideRequestId()), event);
  }
}
