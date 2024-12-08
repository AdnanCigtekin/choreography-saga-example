package com.adnancigtekin.saga.order.mapper;


import com.adnancigtekin.saga.order.dto.OrderDto;
import com.adnancigtekin.saga.order.model.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    Order toOrder(OrderDto orderDto);
    Order fromOrder(OrderDto orderDto);
}
