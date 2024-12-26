package com.juan.adx.task.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.juan.adx.task.mapper.adx.ScheduleMapper;
import com.juan.adx.model.entity.ScheduleJob;

@Service
public class SchedulerService {
	
	@Resource
	private ScheduleMapper scheduleMapper;

	public List<ScheduleJob> allScheduleJob() {
		return this.scheduleMapper.allScheduleJob();
	}

	public void updateRestart(Integer id, int type) {
		this.scheduleMapper.updateRestart(id, type);
	}

}
