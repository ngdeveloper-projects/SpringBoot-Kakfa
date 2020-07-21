package com.ngdeveloper.ngdeveloperkafkaproducer.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class AutoCreateConfig {

	@Bean
	public NewTopic createNgDevTopic() {
		return TopicBuilder.name("ngdev-topic").partitions(3).replicas(3).build();
	}

}
