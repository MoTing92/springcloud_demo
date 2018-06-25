package com.moting.springboot.quartz.task;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

@Configuration
@Component
@EnableScheduling
public class ScheduleTask {

	private static final Logger LOGGER =  LoggerFactory.getLogger(ScheduleTask.class);  
    public void sayHello(){  
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	LOGGER.info("定时任务示例："+sdf.format(new Date()));  
    	System.err.println("定时任务示例："+sdf.format(new Date()));
    }
}
