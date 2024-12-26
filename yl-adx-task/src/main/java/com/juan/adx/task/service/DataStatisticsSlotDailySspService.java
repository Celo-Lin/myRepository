package com.juan.adx.task.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.juan.adx.task.mapper.adx.DataStatisticsSlotDailySspMapper;
import com.juan.adx.model.entity.manage.DataStatisticsSlotDailySsp;

@Service
public class DataStatisticsSlotDailySspService {

	@Resource
	private DataStatisticsSlotDailySspMapper slotDailySspMapper;
	
	public boolean saveSlotDailySsp(DataStatisticsSlotDailySsp slotDailySsp) {
		int ret = this.slotDailySspMapper.saveSlotDailySsp(slotDailySsp);
		return ret > 0;
	}
	
	public boolean updateSlotDailySsp(DataStatisticsSlotDailySsp slotDailySsp) {
		int ret = this.slotDailySspMapper.updateSlotDailySsp(slotDailySsp);
		return ret > 0;
	}

	public boolean updateSlotDailySspIncome(DataStatisticsSlotDailySsp slotDailySsp) {
		int ret = this.slotDailySspMapper.updateSlotDailySspIncome(slotDailySsp);
		return ret > 0;
	}

	public long statisticsSspEstimateIncome(int sspPartnerId, long startTime, long endTime) {
		Long retSum = this.slotDailySspMapper.statisticsSspEstimateIncome(sspPartnerId,startTime, endTime);
		return retSum == null ? 0l : retSum;
	}
}
