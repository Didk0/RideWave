package com.ride.wave.driver.service.impl;

import com.ride.wave.driver.dto.DriverDto;
import com.ride.wave.driver.entity.Driver;
import com.ride.wave.driver.payload.CreateDriverRequest;
import com.ride.wave.driver.repository.DriverRepository;
import com.ride.wave.driver.service.DriverService;
import com.ride.wave.shared.enums.DriverStatus;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {

  private final DriverRepository driverRepository;
  private final ModelMapper modelMapper;

  @Override
  @Transactional
  public DriverDto createDriver(final CreateDriverRequest request) {

    final Driver driver =
        Driver.builder()
            .name(request.getName())
            .vehicle(request.getVehicle())
            .status(DriverStatus.AVAILABLE)
            .build();

    driverRepository.save(driver);

    return modelMapper.map(driver, DriverDto.class);
  }

  @Override
  @Transactional(readOnly = true)
  public DriverDto getDriver(final Long id) {

    final Driver driver =
        driverRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("Driver with id " + id + " not found."));

    return modelMapper.map(driver, DriverDto.class);
  }

  @Override
  @Transactional(readOnly = true)
  public List<DriverDto> getAllDrivers() {

    return driverRepository.findAll().stream()
        .map(driver -> modelMapper.map(driver, DriverDto.class))
        .toList();
  }

  @Override
  @Transactional(readOnly = true)
  public List<DriverDto> getAvailableDrivers() {

    return driverRepository.findAllByStatus(DriverStatus.AVAILABLE).stream()
        .map(driver -> modelMapper.map(driver, DriverDto.class))
        .toList();
  }

  @Override
  @Transactional(readOnly = true)
  public DriverDto getDifferentDriver(final Long driverId, final String lastAssignedTripId) {

    return driverRepository.findAllByStatus(DriverStatus.AVAILABLE).stream()
        .filter(driver -> !driver.getLastAssignedTripId().equals(lastAssignedTripId))
        .filter(driver -> !driver.getId().equals(driverId))
        .findFirst()
        .map(driver -> modelMapper.map(driver, DriverDto.class))
        .orElse(null);
  }

  @Override
  @Transactional
  public DriverDto updateDriver(final Long id, final CreateDriverRequest request) {

    final Driver driver =
        driverRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("Driver with id " + id + " not found."));

    driver.setName(request.getName());
    driver.setVehicle(request.getVehicle());

    driverRepository.save(driver);

    return modelMapper.map(driver, DriverDto.class);
  }

  @Override
  @Transactional
  public void deleteDriver(final Long id) {

    driverRepository.deleteById(id);
  }

  @Override
  @Transactional
  public DriverDto updateDriverStatus(
      final DriverDto driverToUpdate, final DriverStatus driverStatus) {

    driverToUpdate.setStatus(driverStatus);

    final Driver updatedDriver = modelMapper.map(driverToUpdate, Driver.class);

    driverRepository.save(updatedDriver);

    return modelMapper.map(updatedDriver, DriverDto.class);
  }

  @Override
  @Transactional
  public void updateDriverLastAssignedTripId(final Long driverId, final String tripId) {

    final Driver driver =
        driverRepository
            .findById(driverId)
            .orElseThrow(
                () -> new ResourceNotFoundException("Driver with id " + driverId + " not found"));

    driver.setLastAssignedTripId(tripId);

    driverRepository.save(driver);
  }
}
