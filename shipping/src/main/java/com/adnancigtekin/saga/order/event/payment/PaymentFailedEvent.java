package com.adnancigtekin.saga.order.event.payment;


import com.adnancigtekin.saga.order.dto.OrderDto;
import com.adnancigtekin.saga.order.event.EventBase;
import lombok.Data;

import java.util.UUID;

@Data
public class PaymentFailedEvent extends EventBase {

    private String orderId;
    private String status;
    private OrderDto details;

    public PaymentFailedEvent(String orderId, String status, OrderDto details){
        this.orderId = orderId;
        this.status = status;
        this.details = details;
        super.setEventId(UUID.randomUUID());
        super.setType(this.getClass().getSimpleName());
    }
}
