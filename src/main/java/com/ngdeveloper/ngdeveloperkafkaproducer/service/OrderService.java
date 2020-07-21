package com.ngdeveloper.ngdeveloperkafkaproducer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ngdeveloper.ngdeveloperkafkaproducer.dto.Order;
import com.ngdeveloper.ngdeveloperkafkaproducer.producer.OrderKafkaProducer;

@Service
public class OrderService {

	@Autowired
	OrderKafkaProducer orderKafkaProducer;

	public void postOrder(Order order) throws JsonProcessingException {
		orderKafkaProducer.sendOrderToConsumers(order);
	}
}
