package com.ride.wave.trip.repository;

import com.ride.wave.trip.entity.Trip;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TripRepository extends MongoRepository<Trip, String> {

  List<Trip> findByRiderId(Long riderId);

  List<Trip> findByDriverId(Long driverId);

  List<Trip> findByRiderIdAndDriverId(Long riderId, Long driverId);
}
