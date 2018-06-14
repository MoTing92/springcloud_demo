package com.mvne.springcloud.rabbitmq.receiver;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "hello2")
public class Receiver2 {

	@RabbitHandler
	public void process(String hello) {
		System.out.println("Receiver:"+hello);
	}
}
