package com.jim.springkafka.dispatch.service;

import com.jim.springkafka.dispatch.message.OrderCreated;
import com.jim.springkafka.dispatch.util.EventDateTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class DispatchServiceTest {

    private  DispatchService dispatchService;

    @BeforeEach
    void setUp() {
        dispatchService = new DispatchService();
    }

    @Test
    void process() {
        OrderCreated testEvent = EventDateTest.buildOrderCreatedEvent(UUID.randomUUID(),
                UUID.randomUUID().toString());
        dispatchService.process(testEvent);
    }
}