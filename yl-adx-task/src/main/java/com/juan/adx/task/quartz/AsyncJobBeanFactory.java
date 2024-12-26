package com.juan.adx.task.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class AsyncJobBeanFactory extends QuartzJobBean {

	public static final Logger logger = LoggerFactory.getLogger( AsyncJobBeanFactory.class );


	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		
	}

}
