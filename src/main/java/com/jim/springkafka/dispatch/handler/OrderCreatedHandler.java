package com.jim.springkafka.dispatch.handler;

import com.jim.springkafka.dispatch.message.OrderCreated;
import com.jim.springkafka.dispatch.service.DispatchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class OrderCreatedHandler {

    private final DispatchService dispatchService;

    @KafkaListener(
            id = "orderCustomerClient",
            topics = "order.created",
            groupId = "dispatch.order.created.consumer"
    )
    public void consumeMessage(OrderCreated payload){
        log.info("Received message payload: {}", payload);
        dispatchService.process(payload);
    }
}
