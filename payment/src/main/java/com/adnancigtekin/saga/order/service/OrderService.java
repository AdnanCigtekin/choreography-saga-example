package com.adnancigtekin.saga.order.service;

import com.adnancigtekin.saga.order.dto.OrderDto;
import org.springframework.stereotype.Service;


public interface OrderService {
    public boolean createOrder(OrderDto order);
}
