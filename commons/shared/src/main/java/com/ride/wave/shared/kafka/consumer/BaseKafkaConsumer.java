package com.ride.wave.shared.kafka.consumer;

public interface BaseKafkaConsumer<T> {

  void receiveEvent(T event);
}
