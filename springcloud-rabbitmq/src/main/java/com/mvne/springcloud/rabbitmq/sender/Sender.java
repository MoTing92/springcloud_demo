package com.mvne.springcloud.rabbitmq.sender;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Sender {

	@Autowired
	private AmqpTemplate amqpTemplate;
	
	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
	
	public void send() {
		String context = "这里是convertAndSend的第二个参数--hello,"+simpleDateFormat.format(new Date());
		System.out.println("Sender:"+context);
		this.amqpTemplate.convertAndSend("hello",context);
	}
	
	public void send2() {
		String context = "这里是convertAndSend的第二个参数--hello2,"+simpleDateFormat.format(new Date());
		System.out.println("Sender:"+context);
		this.amqpTemplate.convertAndSend("hello2",context);
	}
	
	public void send3() {
		String context = "这里是convertAndSend的第二个参数--hello3,"+simpleDateFormat.format(new Date());
		System.out.println("Sender:"+context);
		this.amqpTemplate.convertAndSend("hello3",context);
	}
}
