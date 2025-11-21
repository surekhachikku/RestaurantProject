package com.restaurant.restaurantservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.restaurant.restaurantservice.dto.RestaurantDTO;
import com.restaurant.restaurantservice.entity.Restaurant;


@Mapper
public interface RestaurantMapper {


	RestaurantMapper INSTANCE = Mappers.getMapper(RestaurantMapper.class);
	
	Restaurant mapRestaurantDTOToRestaurant(RestaurantDTO restaurantDTO);
	
	RestaurantDTO mapRestaurantToRestaurantDTO(Restaurant restaurant);
}
