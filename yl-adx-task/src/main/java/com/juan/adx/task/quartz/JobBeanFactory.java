package com.juan.adx.task.quartz;

import java.util.ArrayList;
import java.util.List;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.juan.adx.task.config.BeanUtil;
import com.juan.adx.task.schedule.AbstractJob;
import com.juan.adx.task.schedule.JobInterface;
import com.juan.adx.model.entity.ScheduleJob;


@DisallowConcurrentExecution
public class JobBeanFactory extends QuartzJobBean {


	public static final Logger logger = LoggerFactory.getLogger( JobBeanFactory.class );
	
	private static final List<String> jobWhiteList = new ArrayList<String>();
	
	private ApplicationContext applicationContext = BeanUtil.getApplicationContext();
	
	static {
		jobWhiteList.add("");
	}
	
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		
		ScheduleJob scheduleJob = ( ScheduleJob )context.getMergedJobDataMap().get( "scheduleJob" );
		String jobName = scheduleJob.getJobName();
		try {
			//AbstractJob abstractJob =  (AbstractJob) Class.forName(scheduleJob.getClassPath()).newInstance();
			Object job = applicationContext.getBean(scheduleJob.getInstanceName());
			AbstractJob abstractJob = null;
			if(job instanceof JobInterface) {
				abstractJob = (AbstractJob) job;
			}
			if(abstractJob == null) {
			//if(abstractJob == null || !"settle_statement_task".equalsIgnoreCase(scheduleJob.getJobName())) {
				logger.info("JobName[{}] is not JobInterface type", scheduleJob.getJobName());
				return;
			}
			logger.info( "JobBeanFactory job starting. name:{}", jobName );
			
			abstractJob.before(context);
			if( !abstractJob.isRunning() ) {
				throw new JobExecutionException( scheduleJob.getJobName() + " 未设置 isRunning" );
			}
			
			abstractJob.execute(context);
			
			abstractJob.after(context);
			
			logger.info( "JobBeanFactory job execute done. name:{}", jobName );
			
		} catch( Exception e ) {
			String errorInfo = String.format("JobBeanFactory job execute error name:%s", jobName);
			logger.error( errorInfo, e );
		} 
		
	}

}
