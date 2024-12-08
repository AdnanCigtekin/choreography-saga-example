package com.adnancigtekin.saga.event.order;

import com.adnancigtekin.saga.inventory.dto.OrderDto;
import com.adnancigtekin.saga.event.EventBase;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class OrderFailedEvent extends EventBase {
    private String orderId;
    private String status;
    private OrderDto details;

    public OrderFailedEvent(String orderId, String status, OrderDto details){
        this.orderId = orderId;
        this.status = status;
        this.details = details;
        super.setEventId(UUID.randomUUID());
        super.setType(this.getClass().getSimpleName());
    }
}
