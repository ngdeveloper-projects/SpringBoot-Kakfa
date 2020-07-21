package com.ngdeveloper.ngdeveloperkafkaproducer.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ngdeveloper.ngdeveloperkafkaproducer.dto.Order;

@Component
public class OrderKafkaProducer {

	private static final Logger log = LoggerFactory.getLogger(OrderKafkaProducer.class);

	@Autowired
	KafkaTemplate<Integer, String> kafkaTemplate;

	@Autowired
	ObjectMapper objectMapper;

	public void sendOrderToConsumers(Order order) throws JsonProcessingException {

		Integer key = order.getOrderId();
		String value = objectMapper.writeValueAsString(order);

		ListenableFuture<SendResult<Integer, String>> listenableFuture = kafkaTemplate.send("ngdev-topic", key, value);
		listenableFuture.addCallback(new ListenableFutureCallback<SendResult<Integer, String>>() {
			@Override
			public void onFailure(Throwable ex) {
				handleFailure(key, value, ex);
			}

			@Override
			public void onSuccess(SendResult<Integer, String> result) {
				handleSuccess(key, value, result);
			}
		});

	}

	private void handleFailure(Integer key, String value, Throwable ex) {
		log.error("Error while Sending the Message and the exception is {}", ex.getMessage());
		try {
			throw ex;
		} catch (Throwable throwable) {
			log.error("Error in OnFailure listenable callback : {}", throwable.getMessage());
		}

	}

	private void handleSuccess(Integer key, String value, SendResult<Integer, String> result) {
		log.info("Message Sent SuccessFully. key : {} and the value is {} , partition used is {}", key, value,
				result.getRecordMetadata().partition());
	}
}
