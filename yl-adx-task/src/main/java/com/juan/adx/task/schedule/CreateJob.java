package com.juan.adx.task.schedule;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.quartz.Job;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.juan.adx.task.quartz.JobBeanFactory;
import com.juan.adx.task.quartz.QuartzManager;
import com.juan.adx.task.quartz.ScheduleState;
import com.juan.adx.task.service.SchedulerService;
import com.juan.adx.model.entity.ScheduleJob;
import com.juan.adx.model.enums.ScheduleRestartType;

@Component
public class CreateJob {
	
	private static final Logger logger = LoggerFactory.getLogger(CreateJob.class);

	@Autowired
	private QuartzManager quartzManage;
	
	@Autowired
	private SchedulerService schedulerService;
	
	
	/**
	 * @Descripteion 创建任务
	 */
	@Scheduled(cron = "0/5 * * * * ?")
	public void execute() throws Exception {
		
		List<ScheduleJob> scheduleJobs = this.schedulerService.allScheduleJob();
		if(scheduleJobs == null || scheduleJobs.isEmpty()) {
			return;
		}
		for (ScheduleJob scheduleJob : scheduleJobs) {
			try {
				
				this.createJob(scheduleJob);
					
			} catch (Exception e) {
				String errorInfo = String.format("CreateJob create job error. jobName:%s", scheduleJob.getJobName());
				logger.error(errorInfo, e);
			}
		}

	}
	
	public void createJob(ScheduleJob scheduleJob) {
		
		String jobName = scheduleJob.getJobName();
		String jobGroupName = scheduleJob.getJobGroupName();
		String triggerName = jobName;
		String triggerGroupName = jobGroupName;
		try {
			String state = quartzManage.getJobState(jobName, jobGroupName);
			
			if (!scheduleJob.getStatus().booleanValue() && !StringUtils.equals(state, ScheduleState.STATUS_NONE)) {
				quartzManage.removeJob(jobName, jobGroupName, triggerName, triggerGroupName);
				logger.info("CreateJob remove job done. name:{}", jobName);
				return;
			}else if(!scheduleJob.getStatus().booleanValue()) {
				return;
			}
			if (scheduleJob.getRestart().booleanValue()) {
				logger.info("CreateJob restart job. jobName:{}", jobName);
				quartzManage.removeJob(jobName, jobGroupName, triggerName, triggerGroupName);
				doJob(scheduleJob);
				logger.info("CreateJob restart job finish. jobName:{}", jobName);
				this.schedulerService.updateRestart(scheduleJob.getId(), ScheduleRestartType.NO_RESTART.getType());
			} else {
				if (StringUtils.equals(state, ScheduleState.STATUS_NONE)) {
					doJob(scheduleJob);
					logger.info("CreateJob start job done. jobName:{}", jobName);
				} else if (StringUtils.equals(state, ScheduleState.STATUS_PAUSED) || StringUtils.equals(state, ScheduleState.STATUS_ERROR)) {
					logger.info("CreateJob paused or error restart job. jobName:{}", jobName);
					quartzManage.removeJob(jobName, jobGroupName, triggerName, triggerGroupName);
					doJob(scheduleJob);
					logger.info("CreateJob paused or error restart job done. jobName:{}", jobName);
				}
			}
		} catch (Exception e) {
			String errorInfo = String.format("CreateJob job schedule error. jobName:%s ", jobName);
			logger.error(errorInfo, e);
		}
		
	}

	/**
	 * 添加任务调度
	 */
	public void doJob(ScheduleJob scheduleJob) throws ClassNotFoundException {
		
		Class<? extends Job> jobClass = JobBeanFactory.class; 
		quartzManage.addJob(scheduleJob, jobClass);
	}
}
