package com.mvne.springcloud.rabbitmq;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.mvne.springcloud.rabbitmq.sender.Sender;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HelloApplicationTest {

	@Autowired
	private Sender sender;
	
	@Test
	public void hello() {
		sender.send();
	}
	
	@Test
	public void hello2() {
		sender.send2();
	}
	
	@Test
	public void hello3() {
		sender.send3();
	}
}
