package com.ride.wave.shared.kafka.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public abstract class BaseKafkaProducer<T> {

  private final KafkaTemplate<String, T> kafkaTemplate;

  protected BaseKafkaProducer(final KafkaTemplate<String, T> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  protected void sendEvent(final String topic, final String key, final T event) {

    kafkaTemplate.send(topic, key, event);

    log.info("Sent event = {} to topic = {}", event, topic);
  }
}
