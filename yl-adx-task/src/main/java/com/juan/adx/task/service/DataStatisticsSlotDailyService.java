package com.juan.adx.task.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.juan.adx.task.mapper.adx.DataStatisticsSlotDailyMapper;
import com.juan.adx.model.entity.manage.DataStatisticsSlotDaily;

@Service
public class DataStatisticsSlotDailyService {

	@Resource
	private DataStatisticsSlotDailyMapper slotDailyMapper;
	
	public boolean saveSlotDaily(DataStatisticsSlotDaily slotDaily) {
		int ret = this.slotDailyMapper.saveSlotDaily(slotDaily);
		return ret > 0;
	}
	
	public boolean updateSlotDaily(DataStatisticsSlotDaily slotDaily) {
		int ret = this.slotDailyMapper.updateSlotDaily(slotDaily);
		return ret > 0;
	}

	public boolean updateSlotDailyIncome(DataStatisticsSlotDaily slotDaily) {
		int ret = this.slotDailyMapper.updateSlotDailyIncome(slotDaily);
		return ret > 0;
	}

	public long statisticsDspEstimateIncome(long startTime, long endTime) {
		Long retSum = this.slotDailyMapper.sumDspEstimateIncomeByDate(startTime, endTime);
		return retSum == null ? 0l : retSum;
	}
}
