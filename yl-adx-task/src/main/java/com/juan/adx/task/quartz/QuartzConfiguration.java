package com.juan.adx.task.quartz;

import java.util.Properties;

import org.quartz.Scheduler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;


@Configuration
public class QuartzConfiguration {

    @Bean(name = "scheduler")
    public Scheduler scheduler(QuartzJobFactory quartzJobFactory) throws Exception {
    	
        SchedulerFactoryBean factoryBean = new SchedulerFactoryBean();
        
        Properties quartzProperties = new Properties();
        quartzProperties.setProperty("org.quartz.threadPool.threadCount", "20");
        
        factoryBean.setQuartzProperties(quartzProperties);
        factoryBean.setJobFactory(quartzJobFactory);
        factoryBean.afterPropertiesSet();
        factoryBean.setAutoStartup(false);
        Scheduler scheduler = factoryBean.getScheduler();
        scheduler.start();
        
        /*ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
		scheduler.setPoolSize(20);
		scheduler.setThreadNamePrefix("task-");
		scheduler.setAwaitTerminationSeconds(60);
		scheduler.setWaitForTasksToCompleteOnShutdown(true);*/
        return scheduler;
    }
}
