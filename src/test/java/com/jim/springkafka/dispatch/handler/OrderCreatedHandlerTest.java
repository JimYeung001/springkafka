package com.jim.springkafka.dispatch.handler;

import com.jim.springkafka.dispatch.message.OrderCreated;
import com.jim.springkafka.dispatch.service.DispatchService;
import com.jim.springkafka.dispatch.util.EventDataTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

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
    void consumeMessage_success() throws Exception {
        OrderCreated testEvent = EventDataTest.buildOrderCreatedEvent(UUID.randomUUID(),
                UUID.randomUUID().toString());
        orderCreatedHandler.consumeMessage(testEvent);
        verify(dispatchService, times(1)).process(testEvent);

    }

    @Test
    void consumeMessage_throwException() throws Exception {
        OrderCreated testEvent = EventDataTest.buildOrderCreatedEvent(UUID.randomUUID(),
                UUID.randomUUID().toString());
        doThrow(new RuntimeException("Error")).when(dispatchService).process(testEvent);
        orderCreatedHandler.consumeMessage(testEvent);
        verify(dispatchService, times(1)).process(testEvent);

    }
}