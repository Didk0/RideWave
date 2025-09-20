package com.ride.wave.driver.service.impl;

import static com.ride.wave.shared.constants.KafkaConstants.DRIVER_ASSIGNMENT_REQUESTS_TOPIC;
import static com.ride.wave.shared.constants.KafkaConstants.DRIVER_SERVICE_GROUP_ID;

import com.ride.wave.driver.dto.DriverDto;
import com.ride.wave.driver.enums.DriverStatus;
import com.ride.wave.driver.service.DriverAssignedProducer;
import com.ride.wave.driver.service.DriverAssignmentRequestConsumer;
import com.ride.wave.driver.service.DriverService;
import com.ride.wave.shared.dto.event.DriverAssignedEvent;
import com.ride.wave.shared.dto.event.DriverAssignmentRequestedEvent;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DriverAssignmentRequestConsumerImpl implements DriverAssignmentRequestConsumer {

  private final DriverService driverService;
  private final DriverAssignedProducer driverAssignedProducer;

  @Override
  @KafkaListener(topics = DRIVER_ASSIGNMENT_REQUESTS_TOPIC, groupId = DRIVER_SERVICE_GROUP_ID)
  public void receiveDriverAssignmentRequestedEvent(final DriverAssignmentRequestedEvent event) {

    log.info("Received Driver Assignment Requested event: {}", event);

    final DriverDto driverToAssign =
        driverService.updateDriverStatus(
            driverService.getAvailableDrivers().getFirst(), DriverStatus.BUSY);

    final DriverAssignedEvent driverAssignedEvent =
        DriverAssignedEvent.builder()
            .tripId(event.tripId())
            .driverId(String.valueOf(driverToAssign.getId()))
            .driverName(driverToAssign.getName())
            .vehicle(driverToAssign.getVehicle())
            .startedAt(Instant.now())
            .build();

    driverAssignedProducer.sendDriverAssignedEvent(driverAssignedEvent);
  }
}
