package com.jim.springkafka.dispatch.service;

import com.jim.springkafka.dispatch.message.OrderCreated;
import com.jim.springkafka.dispatch.message.OrderDispatched;
import com.jim.springkafka.dispatch.util.EventDataTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class DispatchServiceTest {

    private  DispatchService dispatchService;
    KafkaTemplate<String, Object> kafkaTemplateMock;

    @BeforeEach
    void setUp() {
        kafkaTemplateMock  = mock(KafkaTemplate.class);
        dispatchService = new DispatchService(kafkaTemplateMock);
    }

    @Test
    void process_success() throws Exception {
        when(kafkaTemplateMock.send(anyString(), any(Object.class))).thenReturn(mock(CompletableFuture.class));
        OrderCreated testEvent = EventDataTest.buildOrderCreatedEvent(UUID.randomUUID(),
                UUID.randomUUID().toString());
        dispatchService.process(testEvent);
        verify(kafkaTemplateMock, times(1)).send(eq("order.dispatched"), any(Object.class));
    }

    @Test
    void process_throw_exception() throws Exception {
        OrderCreated testEvent = EventDataTest.buildOrderCreatedEvent(UUID.randomUUID(),
                UUID.randomUUID().toString());
        doThrow(new RuntimeException("Error")).when(kafkaTemplateMock).send(eq("dispatch.tracking"), any());
        Exception exception = assertThrows(RuntimeException.class, () -> {
            dispatchService.process(testEvent);
        });
        verify(kafkaTemplateMock, times(1)).send(eq("dispatch.tracking"), any());
        assertThat(exception.getMessage()).isEqualTo("Error");
    }
}