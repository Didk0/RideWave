package com.ride.wave.trip.dto.request;

public record CreateTripRequest(Long riderId, String pickup, String destination) {}
