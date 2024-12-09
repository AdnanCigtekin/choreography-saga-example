package com.adnancigtekin.saga.order.listener;


import com.adnancigtekin.saga.event.OrderEvent;
import com.adnancigtekin.saga.order.model.Order;
import com.adnancigtekin.saga.order.repository.OrderRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;



@Component
@Slf4j
@AllArgsConstructor
public class PaymentFailedListener {

    private final KafkaTemplate<String, OrderEvent> kafkaOrderFailedTemplate;
    private final OrderRepository orderRepository;

    @KafkaListener(
            topics = "paymentTopic",
            groupId = "payment-failed",
            containerFactory = "kafkaPaymentFailedListenerContainerFactory"
    )
    public void listen(OrderEvent event){
        if(!event.getType().equals("payment-failed")){
            return;
        }
        log.info("Received Following Payment Failure Event: {}",event);

        Order myOrder = orderRepository.findById(event.getOrderId()).get();
        myOrder.setStatus("FAILED");
        orderRepository.save(myOrder);
        OrderEvent failedEvent = new OrderEvent(event.getOrderId(), "FAILED",event.getDetails(),"order-failed");
        kafkaOrderFailedTemplate.send("orderTopic",failedEvent);
    }
}
