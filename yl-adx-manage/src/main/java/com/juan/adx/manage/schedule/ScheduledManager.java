package com.juan.adx.manage.schedule;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ScheduledManager {
	
	
	@Resource
	private SysParameterRefresh sysParameterRefresh;

	
	
	@Scheduled(cron = "0 */1 * * * ? ") 
	public void sysParameterRefresh() {
		log.info("********* refresh system parameter start *********");
		this.sysParameterRefresh.exc();
		log.info("********* refresh csystem parameter done *********");
	}
	
	
	
	
	
}
