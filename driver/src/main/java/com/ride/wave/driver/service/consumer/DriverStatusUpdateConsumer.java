package com.ride.wave.driver.service.consumer;

import static com.ride.wave.shared.constants.KafkaConstants.DRIVER_SERVICE_GROUP_ID;
import static com.ride.wave.shared.constants.KafkaConstants.DRIVER_STATUS_UPDATE_TOPIC;

import com.ride.wave.driver.dto.DriverDto;
import com.ride.wave.driver.service.DriverService;
import com.ride.wave.driver.service.producer.DriverAssignedProducer;
import com.ride.wave.shared.dto.event.DriverAssignedEvent;
import com.ride.wave.shared.dto.event.DriverStatusUpdateEvent;
import com.ride.wave.shared.enums.DriverStatus;
import com.ride.wave.shared.kafka.consumer.BaseKafkaConsumer;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DriverStatusUpdateConsumer implements BaseKafkaConsumer<DriverStatusUpdateEvent> {

  private final DriverService driverService;
  private final DriverAssignedProducer driverAssignedProducer;

  @Override
  @KafkaListener(topics = DRIVER_STATUS_UPDATE_TOPIC, groupId = DRIVER_SERVICE_GROUP_ID)
  public void receiveEvent(final DriverStatusUpdateEvent event) {

    log.info("Received Driver Status Update event: {}", event);

    if (DriverStatus.BUSY.equals(event.status())) {

      driverService.updateDriverStatus(
          driverService.getDriver(event.driverId()), DriverStatus.BUSY);

    } else if (DriverStatus.REJECTED.equals(event.status())) {

      final DriverDto driverDto = driverService.getDriver(event.driverId());

      driverService.updateDriverStatus(driverDto, DriverStatus.AVAILABLE);

      final DriverDto differentDriver =
          driverService.getDifferentDriver(event.driverId(), driverDto.getLastAssignedTripId());

      if (differentDriver == null) {
        log.warn("Available driver not found");
        return;
      }

      final DriverDto driverToAssign =
          driverService.updateDriverStatus(differentDriver, DriverStatus.ASSIGNED);

      final DriverAssignedEvent driverAssignedEvent =
          DriverAssignedEvent.builder()
              .tripId(String.valueOf(driverDto.getLastAssignedTripId()))
              .driverId(String.valueOf(driverToAssign.getId()))
              .driverName(driverToAssign.getName())
              .vehicle(driverToAssign.getVehicle())
              .startedAt(Instant.now())
              .build();

      driverAssignedProducer.sendDriverAssignedEvent(driverAssignedEvent);

    } else if (DriverStatus.AVAILABLE.equals(event.status())) {

      driverService.updateDriverStatus(
          driverService.getDriver(event.driverId()), DriverStatus.AVAILABLE);
    }
  }
}
