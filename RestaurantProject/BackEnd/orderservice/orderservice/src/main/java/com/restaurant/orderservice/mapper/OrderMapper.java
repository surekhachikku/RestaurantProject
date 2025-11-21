package com.restaurant.orderservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.restaurant.orderservice.dto.OrderDTO;
import com.restaurant.orderservice.entity.Order;

@Mapper
public interface OrderMapper {

	OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);
	
	Order mapOrderDtoTOOrder(OrderDTO orderDto);
	
	OrderDTO mapOrderToOrderDTO(Order order);
}
