package com.juan.adx.model.entity;

import lombok.Data;

@Data
public class ScheduleJob {

	private Integer id;
	
	private String jobName;
	
	private String jobGroupName;
	
	private Boolean status;
	
	private String instanceName;
	
	private Boolean concurrent;
	
	private Boolean restart;
	
	private String cron;
	
	private String description;
	
	private Long restartTime;
	
	private Long lastRunTime;
	
}
