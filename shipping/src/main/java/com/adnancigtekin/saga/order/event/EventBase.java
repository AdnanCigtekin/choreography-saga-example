package com.adnancigtekin.saga.order.event;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class EventBase {
    private UUID eventId;
    private String type;
}
