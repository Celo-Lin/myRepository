package com.juan.adx.task.bootstrap;

import javax.annotation.Resource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.juan.adx.task.job.AdvertSlotDataRefresh;

@Component
public class StartupRunner implements CommandLineRunner {

	@Resource
	private AdvertSlotDataRefresh advertSlotDataRefresh;

	@Override
	public void run(String... args) throws Exception {
		
		this.advertSlotDataRefresh.execute(null);
		
	}
	
	
}
