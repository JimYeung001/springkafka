package com.jim.springkafka.dispatch.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaProducer {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;
    public void sendMessage(String topic, Object payload) {
        log.info("Sending payload: {} to topic: {}", payload, topic);
        kafkaTemplate.send(topic, payload);
    }
}
