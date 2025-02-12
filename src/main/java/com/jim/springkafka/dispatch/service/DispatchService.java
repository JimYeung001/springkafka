package com.jim.springkafka.dispatch.service;

import com.jim.springkafka.dispatch.message.OrderCreated;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class DispatchService {
    private static final String ORDER_DISPATCHED = "order.dispatched";
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void process(OrderCreated payload) throws ExecutionException, InterruptedException {
        OrderCreated orderCreated = OrderCreated.builder()
                .orderId(payload.getOrderId())
                .item(payload.getItem())
                .build();
        kafkaTemplate.send(ORDER_DISPATCHED, orderCreated.getOrderId()).get();
    }
}
