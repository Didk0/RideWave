package com.ride.wave.driver.service.impl;

import com.ride.wave.driver.dto.DriverDto;
import com.ride.wave.driver.entity.Driver;
import com.ride.wave.driver.enums.DriverStatus;
import com.ride.wave.driver.payload.CreateDriverRequest;
import com.ride.wave.driver.repository.DriverRepository;
import com.ride.wave.driver.service.DriverService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {

  private final DriverRepository driverRepository;
  private final ModelMapper modelMapper;

  @Override
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
  public DriverDto getDriver(final Long id) {

    final Driver driver =
        driverRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("Driver with id " + id + " not found."));

    return modelMapper.map(driver, DriverDto.class);
  }

  @Override
  public List<DriverDto> getAllDrivers() {

    return driverRepository.findAll().stream()
        .map(driver -> modelMapper.map(driver, DriverDto.class))
        .toList();
  }

  @Override
  public List<DriverDto> getAvailableDrivers() {

    return driverRepository.findAllByStatus(DriverStatus.AVAILABLE).stream()
        .map(driver -> modelMapper.map(driver, DriverDto.class))
        .toList();
  }

  @Override
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
  public void deleteDriver(final Long id) {

    driverRepository.deleteById(id);
  }

  @Override
  public DriverDto updateDriverStatus(
      final DriverDto driverToAssign, final DriverStatus driverStatus) {

    driverToAssign.setStatus(driverStatus);

    final Driver updatedDriver = modelMapper.map(driverToAssign, Driver.class);

    driverRepository.save(updatedDriver);

    return modelMapper.map(updatedDriver, DriverDto.class);
  }
}
