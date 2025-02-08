package com.jim.springkafka.dispatch.handler;

import com.jim.springkafka.dispatch.service.DispatchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        orderCreatedHandler.consumeMessage("payload");
        verify(dispatchService, times(1)).process("payload");

    }
}