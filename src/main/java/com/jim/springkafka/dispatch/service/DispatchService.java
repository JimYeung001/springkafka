package com.jim.springkafka.dispatch.service;

import com.jim.springkafka.dispatch.message.DispatchPreparing;
import com.jim.springkafka.dispatch.message.OrderCreated;
import com.jim.springkafka.dispatch.message.OrderDispatched;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Slf4j
@Service
@RequiredArgsConstructor
public class DispatchService {
    private static final String ORDER_DISPATCHED_TOPIC = "order.dispatched";
    private static final String DISPATCH_TRACKING_TOPIC = "dispatch.tracking";
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private static final UUID APP_ID = UUID.randomUUID();

    public void process(OrderCreated payload) throws ExecutionException, InterruptedException {
        DispatchPreparing dispatchPreparing = DispatchPreparing.builder()
                .orderId(payload.getOrderId())
                .build();
        kafkaTemplate.send(DISPATCH_TRACKING_TOPIC, dispatchPreparing).get();

        OrderDispatched orderDispatched = OrderDispatched.builder()
                .orderId(payload.getOrderId())
                .processedById(APP_ID)
                .notes("Order dispatched"+ payload.getOrderId())
                .build();
        kafkaTemplate.send(ORDER_DISPATCHED_TOPIC, orderDispatched).get();
        log.info("Order dispatched: orderID {}, - processedbyid {}", payload.getOrderId(), APP_ID);
    }
}
