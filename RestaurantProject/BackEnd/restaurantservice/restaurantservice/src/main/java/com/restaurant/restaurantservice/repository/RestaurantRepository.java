package com.restaurant.restaurantservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.restaurant.restaurantservice.entity.Restaurant;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer>{

}
