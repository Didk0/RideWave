package com.ride.wave.rider.repository;

import com.ride.wave.rider.entity.Rider;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RiderRepository extends JpaRepository<Rider, Long> {}
