package com.adnancigtekin.saga.event.inventory;

import com.adnancigtekin.saga.order.dto.OrderDto;
import com.adnancigtekin.saga.event.EventBase;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
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
