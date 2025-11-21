package com.restaurant.foodcatalogue.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class FoodItem {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String itemName;
	private String isVeg;
	private String itemDescription;
	private double price;
	@Column(nullable=false, columnDefinition = "INT DEFAULT 1")
	private int quantity;
	private int restaurantId;
	
	
	public FoodItem() {
		
	}

	
	
	public FoodItem(int id, String itemName, String isVeg, String itemDescription, int price, int quantity,
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

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getItemDescription() {
		return itemDescription;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
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
