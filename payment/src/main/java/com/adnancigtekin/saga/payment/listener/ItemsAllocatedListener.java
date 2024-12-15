package com.adnancigtekin.saga.payment.listener;


import com.adnancigtekin.saga.event.OrderEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Component
@Slf4j
@AllArgsConstructor
public class ItemsAllocatedListener {



    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;


    @KafkaListener(
            topics = "inventoryTopic",
            groupId = "items-allocated",
            containerFactory = "ItemsAllocatedListenerContainerFactory"
    )
    public void listen(OrderEvent event){
        if(!event.getType().equals("items-allocated")){
            return;
        }
        log.info("Received Following Items allocated Event: {}",event);
        Calendar c = Calendar.getInstance();
        Integer year = c.get(Calendar.YEAR);
        Integer month = c.get(Calendar.MONTH);

        Integer cardMonth = event.getDetails().getPaymentMethod().getExpirationMonth();
        Integer cardYear = event.getDetails().getPaymentMethod().getExpirationYear();

        if(cardYear > year || (cardMonth >= month && cardYear.equals(year))){
            log.info("Successfully got payment for : {}",event.getOrderId());
            kafkaTemplate.send("paymentTopic",new OrderEvent(event.getOrderId(),"PENDING",event.getDetails(),"payment-success"));
        }else{
            log.error("Payment failure for : {}",event.getOrderId());
            kafkaTemplate.send("orderTopic",new OrderEvent(event.getOrderId(),"FAILED",event.getDetails(),"payment-failed"));
        }

    }
}
