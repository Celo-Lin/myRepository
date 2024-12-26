package com.juan.adx.task.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.juan.adx.task.mapper.adx.DataStatisticsSlotMonitoringMapper;
import com.juan.adx.model.entity.manage.DataStatisticsSlotMonitoring;

@Service
public class DataStatisticsSlotMonitoringService {

	@Resource
	private DataStatisticsSlotMonitoringMapper slotMonitoringMapper;
	
	public boolean saveSlotMonitoring(DataStatisticsSlotMonitoring slotMonitoring) {
		int ret = this.slotMonitoringMapper.saveSlotMonitoring(slotMonitoring);
		return ret > 0;
	}
	
	public boolean updateSlotMonitoring(DataStatisticsSlotMonitoring slotMonitoring) {
		int ret = this.slotMonitoringMapper.updateSlotMonitoring(slotMonitoring);
		return ret > 0;
	}
}
