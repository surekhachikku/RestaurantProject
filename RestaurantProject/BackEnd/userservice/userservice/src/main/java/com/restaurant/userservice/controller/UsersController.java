package com.restaurant.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.userservice.dto.UsersDTO;
import com.restaurant.userservice.service.UsersService;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UsersController {

	@Autowired
	private UsersService usersService;
	
	@GetMapping("/fetchUserById/{id}")
	public ResponseEntity<UsersDTO> fetchUserById(@PathVariable int id){
		
		return usersService.fetchUserById(id);
	}
	
	@PostMapping("/addUser")
	public ResponseEntity<UsersDTO> addUser(@RequestBody UsersDTO userDTO){
		
		return new ResponseEntity<>(usersService.addUser(userDTO),HttpStatus.CREATED);
	}
}
