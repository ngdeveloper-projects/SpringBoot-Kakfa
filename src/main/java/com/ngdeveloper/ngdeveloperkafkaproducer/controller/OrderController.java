package com.ngdeveloper.ngdeveloperkafkaproducer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ngdeveloper.ngdeveloperkafkaproducer.dto.Order;
import com.ngdeveloper.ngdeveloperkafkaproducer.service.OrderService;

@RestController
@RequestMapping("/v1/order")
public class OrderController {

	@Autowired
	OrderService orderService;

	@PostMapping("/")
	public ResponseEntity<Order> postOrder(@RequestBody Order order) throws JsonProcessingException {
		orderService.postOrder(order);
		return ResponseEntity.status(HttpStatus.CREATED).body(order);
	}
}
