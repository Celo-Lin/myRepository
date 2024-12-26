package com.juan.adx.task.schedule;

import org.quartz.Job;
import org.quartz.JobExecutionContext;

import com.juan.adx.common.exception.ServiceRuntimeException;

public interface JobInterface extends Job {

	public void before(JobExecutionContext context) throws ServiceRuntimeException;

	public void after(JobExecutionContext context) throws ServiceRuntimeException;
	
	public boolean isRunning();
	
	public void setInterrupt( boolean interrupt );
}
