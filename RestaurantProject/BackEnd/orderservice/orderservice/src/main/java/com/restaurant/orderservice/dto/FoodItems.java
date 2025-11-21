package com.restaurant.orderservice.dto;



public class FoodItems {

	private int id;
	private String itemName;
	private String isVeg;
	private String itemDescription;
	private Number price;
	private Integer quantity;
	private Integer restaurantId;	
	private boolean processed = false; // New field
	
	public FoodItems() {
		
	}

	public FoodItems(int id, String itemName, String isVeg, String itemDescription, Number price, Integer quantity,
			Integer restaurantId, boolean processed) {
		super();
		this.id = id;
		this.itemName = itemName;
		this.isVeg = isVeg;
		this.itemDescription = itemDescription;
		this.price = price;
		this.quantity = quantity;
		this.restaurantId = restaurantId;
		this.processed = processed;
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

	public Number getPrice() {
		return price;
	}

	public void setPrice(Number price) {
		this.price = price;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(Integer restaurantId) {
		this.restaurantId = restaurantId;
	}

	public boolean isProcessed() {
		return processed;
	}

	public void setProcessed(boolean processed) {
		this.processed = processed;
	}


	
}
