package com.ride.wave.rider.service.consumer;

import static com.ride.wave.shared.constants.KafkaConstants.RIDER_SERVICE_GROUP_ID;
import static com.ride.wave.shared.constants.KafkaConstants.RIDE_STATUS_UPDATE_TOPIC;

import com.ride.wave.rider.service.RideRequestService;
import com.ride.wave.shared.dto.event.RideStatusUpdateEvent;
import com.ride.wave.shared.kafka.consumer.BaseKafkaConsumer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class RideStatusUpdateConsumer implements BaseKafkaConsumer<RideStatusUpdateEvent> {

  private final RideRequestService rideRequestService;

  @Override
  @KafkaListener(topics = RIDE_STATUS_UPDATE_TOPIC, groupId = RIDER_SERVICE_GROUP_ID)
  public void receiveEvent(final RideStatusUpdateEvent event) {

    log.info("Received Ride Status Update event: {}", event);

    rideRequestService.updateRiderStatus(event);
  }
}
