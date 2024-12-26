package com.juan.adx.api.schedule;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ScheduledManager {
	
	
	@Resource
	private SysParameterRefresh sysParameterRefresh;

	
	@Scheduled(cron = "0 */1 * * * ? ") 
	public void sysParameterRefresh() {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		this.sysParameterRefresh.exc();
		stopWatch.stop();
		log.info("********* refresh csystem parameter done, use time {} millis *********", stopWatch.getTotalTimeMillis());
	}
	
	
	
	
	
}
