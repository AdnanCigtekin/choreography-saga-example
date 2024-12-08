package com.adnancigtekin.saga.order.listener;


import com.adnancigtekin.saga.order.event.payment.PaymentFailedEvent;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;



@Component
@Slf4j
public class PaymentFailedListener {

    @KafkaListener(
            topics = "paymentTopic",
            groupId = "my-group"
    )
    public void listen(PaymentFailedEvent event){
        log.info("Received Following Payment Failure Event: {}",event);
    }
}
