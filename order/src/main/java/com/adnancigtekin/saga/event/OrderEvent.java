package com.adnancigtekin.saga.event;

import com.adnancigtekin.saga.order.dto.OrderDto;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Data
@NoArgsConstructor
public class OrderEvent {
    private UUID eventId;
    private String type;
    private String orderId;
    private String status;
    private OrderDto details;



    public OrderEvent(String orderId, String status, OrderDto details, String type){
        this.orderId = orderId;
        this.status = status;
        this.details = details;
        this.setEventId(UUID.randomUUID());
        this.type = type;
    }
}
