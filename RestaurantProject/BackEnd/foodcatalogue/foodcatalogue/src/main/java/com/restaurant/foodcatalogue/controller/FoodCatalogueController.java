package com.restaurant.foodcatalogue.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.foodcatalogue.dto.FoodCataloguePage;
import com.restaurant.foodcatalogue.dto.FoodItemDTO;
import com.restaurant.foodcatalogue.service.FoodCatalogueService;


@RestController
@RequestMapping("/food")
@CrossOrigin
public class FoodCatalogueController {

	@Autowired
	private FoodCatalogueService foodCatalogueService;
	
	@PostMapping("/addFood")
	public ResponseEntity<FoodItemDTO> addFood(@RequestBody FoodItemDTO foodItemDto){
		
		return new ResponseEntity<>(foodCatalogueService.addFood(foodItemDto),HttpStatus.CREATED);
	}
	
	@GetMapping("/fetchFoodByRestaurantId/{restaurantId}")
	public ResponseEntity<FoodCataloguePage> fetchFoodByRestaurantId(@PathVariable int restaurantId){
		
		return new ResponseEntity<>(foodCatalogueService.fetchFoodByRestaurantId(restaurantId),HttpStatus.OK);
		
	}
	
	@PutMapping("/updateFood/{id}")
	public ResponseEntity<FoodItemDTO> updateFood(@PathVariable int id, @RequestBody FoodItemDTO foodItemDto) {
	    return foodCatalogueService.updateFood(id, foodItemDto);
	}

	@DeleteMapping("/deleteFood/{id}")
	public ResponseEntity<FoodItemDTO> deleteFood(@PathVariable int id) {
	    return foodCatalogueService.deleteRestaurantById(id);
	}
}