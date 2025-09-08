package com.ride.wave.rider.repository;

import com.ride.wave.rider.entity.RideRequest;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RideRequestRepository extends JpaRepository<RideRequest, Long> {

  List<RideRequest> findByRiderId(Long riderId);
}
