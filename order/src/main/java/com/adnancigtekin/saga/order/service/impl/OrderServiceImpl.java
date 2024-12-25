package com.adnancigtekin.saga.order.service.impl;

import com.adnancigtekin.saga.event.OrderEvent;
import com.adnancigtekin.saga.order.dto.OrderDto;
import com.adnancigtekin.saga.order.mapper.OrderMapper;
import com.adnancigtekin.saga.order.model.Order;
import com.adnancigtekin.saga.order.repository.OrderRepository;
import com.adnancigtekin.saga.order.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;




@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;


    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;



    @Override
    public boolean createOrder(OrderDto order) {
        Order orderDocument = orderMapper.toOrder(order);
        orderDocument.setStatus("PENDING");
        Order savedOrder = orderRepository.save(orderDocument);

        OrderEvent orderCreatedEvent = new OrderEvent(savedOrder.getId(),"PENDING",order,"order-created");

        kafkaTemplate.send("orderTopic",orderCreatedEvent);
        return true;
    }
}
