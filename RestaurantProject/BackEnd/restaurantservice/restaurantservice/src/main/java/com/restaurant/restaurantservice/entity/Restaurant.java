package com.restaurant.restaurantservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
/*
								import lombok.AllArgsConstructor;
								import lombok.Data;
								import lombok.NoArgsConstructor;*/


/*
 * @NoArgsConstructor
 * 
 * @AllArgsConstructor
 * 
 * @Data
 */
@Entity
public class Restaurant {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String name;
	private String address;
	private String city;
	private String restaurantDescription;
	private String imageUrl;
	
	public Restaurant() {
		
	}
	
	public Restaurant(int id, String name, String address, String city, String restaurantDescription, String imageUrl) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.city = city;
		this.restaurantDescription = restaurantDescription;
		this.imageUrl = imageUrl;
	}
	
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getAddress() {
		return address;
	}

	public String getCity() {
		return city;
	}

	public String getRestaurantDescription() {
		return restaurantDescription;
	}

	public void setId(int id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public void setRestaurantDescription(String restaurantDescription) {
		this.restaurantDescription = restaurantDescription;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	
}
