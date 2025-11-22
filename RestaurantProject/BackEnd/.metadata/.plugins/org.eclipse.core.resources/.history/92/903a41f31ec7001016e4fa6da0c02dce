package com.restaurant.foodcatalogue.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.restaurant.foodcatalogue.entity.FoodItem;

@Repository
public interface FoodCatalogueRepository extends JpaRepository<FoodItem, Integer>{

	List<FoodItem> findByRestaurantId(int restaurantId);

}
