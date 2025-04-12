package com.jim.springkafka.dispatch.kafka;

import com.jim.springkafka.dispatch.consumer.KafkaConsumer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"})
public class EmbededKafkaIntegrationTest {

    @Autowired
    private KafkaConsumer kafkaConsumer;
    @Autowired
    private com.jim.springkafka.dispatch.producer.KafkaProducer kafkaProducer;

    @Value("${spring.kafka.topic}")
    private String topic;


    @Test
    public void givenEmbeddedKafkaBroker_whenSendingMessage_thenMessageReceived() throws Exception {
        String message = "Hello, Kafka!";
        kafkaProducer.sendMessage(topic, message);
        boolean consumed = kafkaConsumer.getLatch().await(10, TimeUnit.SECONDS);
        assertTrue(consumed);
        assertEquals(message, kafkaConsumer.getPayload());
    }

}
