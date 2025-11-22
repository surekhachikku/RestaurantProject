package com.restaurant.restaurantservice.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.restaurant.restaurantservice.dto.RestaurantDTO;
import com.restaurant.restaurantservice.entity.Restaurant;
import com.restaurant.restaurantservice.mapper.RestaurantMapper;
import com.restaurant.restaurantservice.repository.RestaurantRepository;

@Service
public class RestaurantService {
	
	@Autowired
	private RestaurantRepository restaurantRepository;

	public List<RestaurantDTO> getAllRestaurants() {
		
		List<Restaurant> restaurantList = restaurantRepository.findAll();
		return restaurantList.stream()
				.map(restaurant -> RestaurantMapper.INSTANCE.mapRestaurantToRestaurantDTO(restaurant))
				.collect(Collectors.toList());
	}

	public RestaurantDTO addRestaurants(RestaurantDTO restaurantDto) {
		
		Restaurant restaurant = restaurantRepository.save(RestaurantMapper.INSTANCE.mapRestaurantDTOToRestaurant(restaurantDto));
		return RestaurantMapper.INSTANCE.mapRestaurantToRestaurantDTO(restaurant);
	}

	public ResponseEntity<RestaurantDTO> fetchRestaurantByID(int id) {
		
		Optional<Restaurant> restaurant = restaurantRepository.findById(id);
		if(restaurant.isPresent()) {
			
			return new ResponseEntity<>(RestaurantMapper.INSTANCE.mapRestaurantToRestaurantDTO(restaurant.get()),HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
		}
	}

	public ResponseEntity<RestaurantDTO> deleteRestaurantById(int id) {
		
		 Optional<Restaurant> restaurant = restaurantRepository.findById(id);

		    if (restaurant.isPresent()) {
		        restaurantRepository.deleteById(id);
		        return new ResponseEntity<>(
		            RestaurantMapper.INSTANCE.mapRestaurantToRestaurantDTO(restaurant.get()),
		            HttpStatus.OK);
		    } else {
		        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		    }
	}

	public ResponseEntity<RestaurantDTO> updateRestaurant(int id, RestaurantDTO restaurantDTO) {
		Optional<Restaurant> existingRestaurant = restaurantRepository.findById(id);
		
		if(existingRestaurant.isPresent()) {
			
			restaurantRepository.save(RestaurantMapper.INSTANCE.mapRestaurantDTOToRestaurant(restaurantDTO));
			return new ResponseEntity<>(
		            RestaurantMapper.INSTANCE.mapRestaurantToRestaurantDTO(existingRestaurant.get()),HttpStatus.OK);
		 } else {
		        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		    }
	}

	public ResponseEntity<RestaurantDTO> updateRestaurantWithImage(RestaurantDTO restaurantDTO) {
		
		
		Restaurant restaurant = restaurantRepository.save(RestaurantMapper.INSTANCE.mapRestaurantDTOToRestaurant(restaurantDTO));
		return new ResponseEntity<>(
	            RestaurantMapper.INSTANCE.mapRestaurantToRestaurantDTO(restaurant),HttpStatus.OK);
	}

}
