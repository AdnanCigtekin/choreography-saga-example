package com.adnancigtekin.saga.event;

import com.adnancigtekin.saga.inventory.dto.OrderDto;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Data
@NoArgsConstructor
public class OrderEvent {
    // ID of the specific event
    private UUID eventId;
    // Type of the event
    private String type;
    // ID of the order entry in database
    private String orderId;
    // Status of this event
    private String status;
    // Other details needed for other services such as credit card info, address...etc.
    private OrderDto details;

    public OrderEvent(String orderId, String status, OrderDto details,String type){
        this.orderId = orderId;
        this.status = status;
        this.details = details;
        this.setEventId(UUID.randomUUID());
        this.type = type;
    }
}
