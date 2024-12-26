package com.juan.adx.api.bootstrap;

import javax.annotation.Resource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.juan.adx.api.schedule.SysParameterRefresh;

@Component
public class StartupRunner implements CommandLineRunner {

	
	@Resource
	private SysParameterRefresh sysParameterRefresh;

	@Override
	public void run(String... args) throws Exception {
		
		this.sysParameterRefresh.exc();
		
	}
	
	
}
