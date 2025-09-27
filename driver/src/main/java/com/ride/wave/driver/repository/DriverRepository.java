package com.ride.wave.driver.repository;

import com.ride.wave.driver.entity.Driver;
import com.ride.wave.shared.enums.DriverStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverRepository extends JpaRepository<Driver, Long> {

  List<Driver> findAllByStatus(DriverStatus status);
}
