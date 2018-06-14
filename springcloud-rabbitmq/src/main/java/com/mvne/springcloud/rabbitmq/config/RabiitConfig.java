package com.mvne.springcloud.rabbitmq.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabiitConfig {

	@Bean
	public Queue helloQueue() {
		return new Queue("hello");
	}
	
	@Bean
	public Queue hello2Queue() {
		return new Queue("hello2");
	}
	
	@Bean
	public Queue hello3Queue() {
		return new Queue("hello3");
	}
}
