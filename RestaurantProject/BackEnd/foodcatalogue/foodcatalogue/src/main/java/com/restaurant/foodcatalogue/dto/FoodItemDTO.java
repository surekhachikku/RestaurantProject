package com.restaurant.foodcatalogue.dto;

import jakarta.persistence.Column;

public class FoodItemDTO {
	private int id;
	private String itemName;
	private String isVeg;
	private String itemDescription;
	private double price;

	@Column(nullable=false, columnDefinition = "INT DEFAULT 1")
	private int quantity;
	
	private int restaurantId;
		
	public FoodItemDTO() {
		
	}

	public FoodItemDTO(int id, String itemName, String isVeg, String itemDescription, double price, int quantity,
			int restaurantId) {
		super();
		this.id = id;
		this.itemName = itemName;
		this.isVeg = isVeg;
		this.itemDescription = itemDescription;
		this.price = price;
		this.quantity = quantity;
		this.restaurantId = restaurantId;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getIsVeg() {
		return isVeg;
	}

	public void setIsVeg(String isVeg) {
		this.isVeg = isVeg;
	}

	public String getItemDescription() {
		return itemDescription;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(int restaurantId) {
		this.restaurantId = restaurantId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}
