package com.ride.wave.shared.constants;

public class KafkaConstants {

  private KafkaConstants() {}

  public static final String RIDE_REQUESTS_TOPIC = "ride-requests";
  public static final String DRIVER_ASSIGNMENT_REQUESTS_TOPIC = "driver-assignment-requests";
  public static final String DRIVER_ASSIGNMENTS_TOPIC = "driver-assignments";
  public static final String DRIVER_STATUS_UPDATE_TOPIC = "driver-status-update";
  public static final String RIDE_STATUS_UPDATE_TOPIC = "ride-status-update";

  public static final String TRIP_SERVICE_GROUP_ID = "trip-service-group";
  public static final String DRIVER_SERVICE_GROUP_ID = "driver-service-group";
  public static final String RIDER_SERVICE_GROUP_ID = "rider-service-group";
}
