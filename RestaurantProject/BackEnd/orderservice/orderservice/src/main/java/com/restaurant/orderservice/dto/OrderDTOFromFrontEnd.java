package com.restaurant.orderservice.dto;

import java.util.List;

public class OrderDTOFromFrontEnd {
	private Integer orderId;
	private List<FoodItems> foodItems;
	private Users user;
	private Restaurant restaurant;
	private Double totalPrice;
	private boolean processed = false; // New field
	
	public OrderDTOFromFrontEnd() {
		
	}

	public OrderDTOFromFrontEnd(Integer orderId, List<FoodItems> foodItems, Users user, Restaurant restaurant,
			Double totalPrice, boolean processed) {
		super();
		this.orderId = orderId;
		this.foodItems = foodItems;
		this.user = user;
		this.restaurant = restaurant;
		this.totalPrice = totalPrice;
		this.processed = processed;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public List<FoodItems> getFoodItems() {
		return foodItems;
	}

	public void setFoodItems(List<FoodItems> foodItems) {
		this.foodItems = foodItems;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public boolean isProcessed() {
		return processed;
	}

	public void setProcessed(boolean processed) {
		this.processed = processed;
	}

	
}
