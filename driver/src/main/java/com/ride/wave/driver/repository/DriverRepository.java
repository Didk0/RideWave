package com.ride.wave.driver.repository;

import com.ride.wave.driver.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverRepository extends JpaRepository<Driver, Long> {}
