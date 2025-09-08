package com.ride.wave.trip.payload;

public record CreateTripRequest(Long riderId, String pickup, String destination) {}
