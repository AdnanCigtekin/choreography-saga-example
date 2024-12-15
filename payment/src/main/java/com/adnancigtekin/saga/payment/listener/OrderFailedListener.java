package com.adnancigtekin.saga.payment.listener;

import com.adnancigtekin.saga.event.OrderEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class OrderFailedListener {



    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;


    @KafkaListener(
            topics = "orderTopic",
            groupId = "order-failure",
            containerFactory = "orderFailedListenerContainerFactory"
    )
    public void listen(OrderEvent event){
        if(!event.getType().equals("order-failed")){
            return;
        }
        log.error("Order failed, so refunding user : {}",event);
    }
}
