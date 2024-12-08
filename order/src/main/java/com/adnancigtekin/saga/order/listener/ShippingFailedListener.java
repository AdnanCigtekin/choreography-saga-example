package com.adnancigtekin.saga.order.listener;


import com.adnancigtekin.saga.event.shipping.ShippingFailedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ShippingFailedListener {

    @KafkaListener(
            topics = "shippingTopic",
            groupId = "my-group"
    )
    public void listen(ShippingFailedEvent event){
        log.info("Received Following Shipping Failure Event: {}",event);
    }
}
