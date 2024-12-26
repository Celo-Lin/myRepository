package com.juan.adx.task.schedule;

import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Service;
@Service
public abstract class AbstractJob implements JobInterface {

	
	public static boolean running = false;

	@Override
	public boolean isRunning() {
		return running;
	}

	@Override
	public void before( JobExecutionContext context ) {
		running = true;
	}

	@Override
	public void after( JobExecutionContext context ) {
		running = false;
	}
	
	public static boolean interrupt = false;
	
	@Override
	public void setInterrupt( boolean flag ) {
		interrupt = flag;
	}
	
}
