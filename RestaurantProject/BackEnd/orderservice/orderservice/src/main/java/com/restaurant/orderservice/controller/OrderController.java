package com.restaurant.orderservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.orderservice.dto.OrderDTO;
import com.restaurant.orderservice.dto.OrderDTOFromFrontEnd;
import com.restaurant.orderservice.service.OrderService;

@RestController
@RequestMapping("/restaurant")
@CrossOrigin
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	@PostMapping("/saveOrder")
	public ResponseEntity<OrderDTO> saveOrder(@RequestBody OrderDTOFromFrontEnd orderDetails){
		
		return new ResponseEntity<>(orderService.saveOrder(orderDetails),HttpStatus.CREATED);
		
	}
	 @GetMapping("/getAllOrders")
	 public ResponseEntity<List<OrderDTO>> getAllOrders() {
	        return new ResponseEntity<>(orderService.getAllOrders(), HttpStatus.OK);
	 }

	    @GetMapping("/getOrdersByUser/{userId}")
	    public ResponseEntity<List<OrderDTO>> getOrdersByUser(@PathVariable int userId) {
	        return new ResponseEntity<>(orderService.getOrdersByUserId(userId), HttpStatus.OK);
	    }

	    @GetMapping("/getOrdersByRestaurant/{restaurantId}")
	    public ResponseEntity<List<OrderDTO>> getOrdersByRestaurant(@PathVariable int restaurantId) {
	        return new ResponseEntity<>(orderService.getOrdersByRestaurantId(restaurantId), HttpStatus.OK);
	    }
	    @PutMapping("/markProcessed/{orderId}")
	    public ResponseEntity<OrderDTO> markProcessed(@PathVariable Integer orderId) {
	    	System.out.println("Inside Order Controller");
	        return new ResponseEntity<>(orderService.markOrderProcessed(orderId), HttpStatus.OK);
	    }
}
