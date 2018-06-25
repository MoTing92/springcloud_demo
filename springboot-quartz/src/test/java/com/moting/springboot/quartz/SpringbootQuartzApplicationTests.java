package com.moting.springboot.quartz;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.moting.springboot.quartz.dao.ConfigRepository;
import com.moting.springboot.quartz.model.Config;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootQuartzApplicationTests {

	@Autowired  
    private ConfigRepository repository; 
	
	@Test
	public void contextLoads() {
		Config config = new Config(1L,"5/20 * * * * ?");
		repository.save(config);
	}

}
