package com.ride.wave.trip.service.impl;

import static com.ride.wave.shared.constants.KafkaConstants.RIDE_REQUESTS_TOPIC;
import static com.ride.wave.shared.constants.KafkaConstants.TRIP_SERVICE_GROUP_ID;

import com.ride.wave.shared.dto.event.DriverAssignmentRequestedEvent;
import com.ride.wave.shared.dto.event.RideRequestedEvent;
import com.ride.wave.trip.dto.response.TripDto;
import com.ride.wave.trip.service.DriverAssignmentRequestProducer;
import com.ride.wave.trip.service.RideRequestConsumer;
import com.ride.wave.trip.service.TripService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class RideRequestConsumerImpl implements RideRequestConsumer {

  private final TripService tripService;
  private final DriverAssignmentRequestProducer driverAssignmentRequestProducer;

  @Override
  @KafkaListener(topics = RIDE_REQUESTS_TOPIC, groupId = TRIP_SERVICE_GROUP_ID)
  public void receiveRideRequestedEvent(final RideRequestedEvent event) {

    log.info("Received Ride Requested event: {}", event);

    final TripDto tripDto = tripService.createTrip(event);

    final DriverAssignmentRequestedEvent driverAssignmentRequestedEvent =
        DriverAssignmentRequestedEvent.builder()
            .tripId(tripDto.getId())
            .pickupLocation(tripDto.getPickup())
            .destinationLocation(tripDto.getDestination())
            .build();

    driverAssignmentRequestProducer.sendDriverAssignmentRequestedEvent(
        driverAssignmentRequestedEvent);
  }
}
