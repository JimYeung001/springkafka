package com.jim.springkafka.dispatch.handler;

import com.jim.springkafka.dispatch.message.OrderCreated;
import com.jim.springkafka.dispatch.service.DispatchService;
import com.jim.springkafka.dispatch.util.EventDateTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderCreatedHandlerTest {

    private  OrderCreatedHandler orderCreatedHandler;
    private DispatchService dispatchService;



    @BeforeEach
    void setUp() {
        dispatchService = mock(DispatchService.class);
        orderCreatedHandler = new OrderCreatedHandler(dispatchService);
    }

    @Test
    void consumeMessage() {
        OrderCreated testEvent = EventDateTest.buildOrderCreatedEvent(UUID.randomUUID(),
                UUID.randomUUID().toString());
        orderCreatedHandler.consumeMessage(testEvent);
        verify(dispatchService, times(1)).process(testEvent);

    }
}