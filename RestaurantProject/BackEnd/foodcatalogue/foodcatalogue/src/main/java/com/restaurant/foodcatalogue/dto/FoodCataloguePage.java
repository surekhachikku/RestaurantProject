package com.restaurant.foodcatalogue.dto;

import java.util.List;

import com.restaurant.foodcatalogue.entity.FoodItem;



public class FoodCataloguePage {

	private List<FoodItem> foodItemList;
	
	private Restaurant restaurant;
	
	public FoodCataloguePage() {
		
	}

	public FoodCataloguePage(List<FoodItem> foodItemList, Restaurant restaurant) {
		super();
		this.foodItemList = foodItemList;
		this.restaurant = restaurant;
	}

	public List<FoodItem> getFoodItemList() {
		return foodItemList;
	}

	public void setFoodItemList(List<FoodItem> foodItemList) {
		this.foodItemList = foodItemList;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}
	
	
}
