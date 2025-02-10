package com.jim.springkafka.dispatch.util;

import com.jim.springkafka.dispatch.message.OrderCreated;

import java.util.UUID;

public class EventDateTest {
    public static OrderCreated buildOrderCreatedEvent(UUID orderId, String item) {
        return OrderCreated.builder()
                .orderId(orderId)
                .item(item)
                .build();
    }
}
