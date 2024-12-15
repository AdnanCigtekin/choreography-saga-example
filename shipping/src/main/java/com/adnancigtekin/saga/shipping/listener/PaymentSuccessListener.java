package com.adnancigtekin.saga.shipping.listener;


import com.adnancigtekin.saga.event.OrderEvent;
import com.adnancigtekin.saga.shipping.model.CargoFirm;
import com.adnancigtekin.saga.shipping.repository.CargoFirmRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;
// TODO : Test if everything is ok
@Component
@Slf4j
@AllArgsConstructor
public class PaymentSuccessListener {


    private final CargoFirmRepository cargoFirmRepository;
    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;


    @KafkaListener(
            topics = "paymentTopic",
            groupId = "payment-success",
            containerFactory = "paymentSuccessListenerContainerFactory"
    )
    public void listen(OrderEvent event){
        if(!event.getType().equals("payment-success")){
            return;
        }
        log.info("Received payment success event for id : {}",event.getOrderId());

        Optional<CargoFirm> cargoFirm = cargoFirmRepository.findById(event.getDetails().getCargoFirmId());

        if (cargoFirm.isPresent()){
            event.setStatus("SUCCESS");
            event.setType("order-success");
            kafkaTemplate.send("orderTopic",event);
        }else{
            event.setStatus("FAILED");
            event.setType("shipping-failed");
            kafkaTemplate.send("orderTopic",event);
        }
    }
}
