package com.ride.wave.driver.service;

import com.ride.wave.driver.dto.DriverDto;
import com.ride.wave.driver.enums.DriverStatus;
import com.ride.wave.driver.payload.CreateDriverRequest;
import java.util.List;

public interface DriverService {

  DriverDto createDriver(CreateDriverRequest request);

  DriverDto getDriver(Long id);

  List<DriverDto> getAllDrivers();

  List<DriverDto> getAvailableDrivers();

  DriverDto updateDriver(Long id, CreateDriverRequest request);

  void deleteDriver(Long id);

  DriverDto updateDriverStatus(DriverDto driverToAssign, DriverStatus driverStatus);
}
