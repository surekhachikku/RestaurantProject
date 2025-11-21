package com.restaurant.orderservice.dto;

import java.util.List;

public class OrderDTO {

	private Integer orderId;
	
	private List<FoodItems> foodItems;
	
	private Restaurant restaurant;
	
	private Double totalPrice;
	
	private Users user;
	
	private boolean processed = false; // New field
	
	public OrderDTO() {
		
	}

	public OrderDTO(Integer orderId, List<FoodItems> foodItems, Restaurant restaurant, Double totalPrice, Users user,
			boolean processed) {
		super();
		this.orderId = orderId;
		this.foodItems = foodItems;
		this.restaurant = restaurant;
		this.totalPrice = totalPrice;
		this.user = user;
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

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public boolean isProcessed() {
		return processed;
	}

	public void setProcessed(boolean processed) {
		this.processed = processed;
	}

	
}
