package com.moting.springboot.quartz.task;


import javax.annotation.Resource;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.moting.springboot.quartz.dao.ConfigRepository;

@Configuration  
@EnableScheduling  
@Component 
public class ScheduleRefreshDatabase {

	@Autowired  
    private ConfigRepository repository;  
  
    @Resource(name = "jobDetail")  
    private JobDetail jobDetail;  
  
    @Resource(name = "jobTrigger")  
    private CronTrigger cronTrigger;  
  
    @Resource(name = "scheduler")  
    private Scheduler scheduler;  
  
    //固定等待时间 @Scheduled(fixedDelay = 时间间隔 )
    //固定间隔时间 @Scheduled(fixedRate = 时间间隔 )
    //Corn表达式 @Scheduled(cron = Corn表达式)
    @Scheduled(fixedRate = 5000) // 每隔5s查库，并根据查询结果决定是否重新设置定时任务  
    public void scheduleUpdateCronTrigger() throws SchedulerException {  
        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(cronTrigger.getKey());  
        String currentCron = trigger.getCronExpression();// 当前Trigger使用的  
        String searchCron = repository.findById(1L).get().getCron();
//        String searchCron = repository.findOne(1L).getCron();// 从数据库查询出来的  
//        String searchCron = "*/10 * * * * ?";
        System.out.println("当前cron："+currentCron);  
        System.out.println("数据库查询出的cron："+searchCron);  
        if (currentCron.equals(searchCron)) {  
            // 如果当前使用的cron表达式和从数据库中查询出来的cron表达式一致，则不刷新任务  
        } else {  
            // 表达式调度构建器  
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(searchCron);  
            // 按新的cronExpression表达式重新构建trigger  
            trigger = (CronTrigger) scheduler.getTrigger(cronTrigger.getKey());  
            trigger = trigger.getTriggerBuilder().withIdentity(cronTrigger.getKey())  
                    .withSchedule(scheduleBuilder).build();  
            // 按新的trigger重新设置job执行  
            scheduler.rescheduleJob(cronTrigger.getKey(), trigger);  
            currentCron = searchCron;  
        }  
    } 
}