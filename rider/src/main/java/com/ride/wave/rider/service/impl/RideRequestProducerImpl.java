package com.ride.wave.rider.service.impl;

import static com.ride.wave.shared.constants.KafkaConstants.RIDE_REQUESTS_TOPIC;

import com.ride.wave.rider.service.RideRequestProducer;
import com.ride.wave.shared.dto.event.RideRequestedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RideRequestProducerImpl implements RideRequestProducer {

  private final KafkaTemplate<String, RideRequestedEvent> kafkaTemplate;

  @Override
  public void sendRideRequestedEvent(final RideRequestedEvent event) {

    kafkaTemplate.send(RIDE_REQUESTS_TOPIC, event.requestId(), event);

    log.info("Sent Ride Requested event: {}", event);
  }
}
