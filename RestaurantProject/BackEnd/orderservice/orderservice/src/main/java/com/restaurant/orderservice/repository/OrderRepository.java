package com.restaurant.orderservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.restaurant.orderservice.entity.Order;

@Repository
public interface OrderRepository extends MongoRepository<Order, Integer>{

    List<Order> findByUser_UserId(int userId);

    List<Order> findByRestaurant_Id(int restaurantId);
    
    List<Order> findByProcessed(boolean processed);
    
    Optional<Order> findByOrderId(Integer orderId);
}
