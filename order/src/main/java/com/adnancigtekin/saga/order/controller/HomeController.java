package com.adnancigtekin.saga.order.controller;

import com.adnancigtekin.saga.order.dto.OrderDto;
import com.adnancigtekin.saga.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order-service")
public class HomeController {

    @Autowired
    private OrderService orderService;

    @PostMapping(path= "/")
    public String sendOrder(@RequestBody OrderDto order){
        orderService.createOrder(order);
        return "";
    }
}
