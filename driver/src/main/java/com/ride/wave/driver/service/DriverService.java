package com.ride.wave.driver.service;

import com.ride.wave.driver.dto.DriverDto;
import com.ride.wave.driver.payload.CreateDriverRequest;
import com.ride.wave.shared.enums.DriverStatus;
import java.util.List;

public interface DriverService {

  DriverDto createDriver(CreateDriverRequest request);

  DriverDto getDriver(Long id);

  List<DriverDto> getAllDrivers();

  List<DriverDto> getAvailableDrivers();

  DriverDto getDifferentDriver(Long driverId, String lastAssignedTripId);

  DriverDto updateDriver(Long id, CreateDriverRequest request);

  void deleteDriver(Long id);

  DriverDto updateDriverStatus(DriverDto driverToUpdate, DriverStatus driverStatus);

  void updateDriverLastAssignedTripId(Long driverId, String tripId);
}
