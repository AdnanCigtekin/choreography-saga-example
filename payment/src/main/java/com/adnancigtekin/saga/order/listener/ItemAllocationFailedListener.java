package com.adnancigtekin.saga.order.listener;


import com.adnancigtekin.saga.order.event.inventory.ItemAllocationFailedEvent;
import com.adnancigtekin.saga.order.event.payment.PaymentFailedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ItemAllocationFailedListener {

    @KafkaListener(
            topics = "inventoryTopic",
            groupId = "my-group"
    )
    public void listen(ItemAllocationFailedEvent event){
        log.info("Received Following Item Allocation Failure Event: {}",event);
    }
}
