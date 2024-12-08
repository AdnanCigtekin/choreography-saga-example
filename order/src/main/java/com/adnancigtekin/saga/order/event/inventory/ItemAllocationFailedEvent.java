package com.adnancigtekin.saga.order.event.inventory;

import com.adnancigtekin.saga.order.dto.OrderDto;
import com.adnancigtekin.saga.order.event.EventBase;
import lombok.Data;

import java.util.UUID;

@Data
public class ItemAllocationFailedEvent extends EventBase {
    private String orderId;
    private String status;
    private OrderDto details;

    public ItemAllocationFailedEvent(String orderId, String status, OrderDto details){
        this.orderId = orderId;
        this.status = status;
        this.details = details;
        super.setEventId(UUID.randomUUID());
        super.setType(this.getClass().getSimpleName());
    }
}
