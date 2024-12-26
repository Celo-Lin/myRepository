package com.juan.adx.task.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.juan.adx.task.mapper.adx.DataStatisticsSlotDailyRealtimeMapper;
import com.juan.adx.model.entity.manage.DataStatisticsSlotDaily;
import com.juan.adx.model.entity.manage.DataStatisticsSlotRealtime;
import com.juan.adx.model.entity.manage.DataStatisticsSlotDailySsp;
import com.juan.adx.model.entity.manage.DataStatisticsSlotMonitoring;
import com.juan.adx.model.entity.manage.DataStatisticsTrendIndex;
import com.juan.adx.model.entity.sspmanage.ChannelDataStatisticsTrendIndex;
import com.juan.adx.model.form.task.SlotStatisticsDataForm;

@Service
public class DataStatisticsSlotDailyRealtimeService {

	@Resource
	private DataStatisticsSlotDailyRealtimeMapper dailyRealtimeMapper;

	public boolean saveSlotRealtimeStatistics(DataStatisticsSlotRealtime slotRealtime) {
		int ret = this.dailyRealtimeMapper.saveSlotRealtimeStatistics(slotRealtime);
		return ret > 0;
	}

	public DataStatisticsSlotRealtime getSlotRealtime(SlotStatisticsDataForm form) {
		return this.dailyRealtimeMapper.querySlotRealtime(form);
	}
	
	public List<DataStatisticsSlotDaily> getSlotDailyStatisticsData(Long startTime, Long endTime) {
		return this.dailyRealtimeMapper.querySlotDailyStatisticsData(startTime, endTime);
	}
	
	public List<DataStatisticsSlotDailySsp> getSlotDailySspStatisticsData(Long startTime, Long endTime) {
		return this.dailyRealtimeMapper.querySlotDailySspStatisticsData(startTime, endTime);
	}

	public boolean updateSlotRealtimeStatistics(DataStatisticsSlotRealtime slotRealtime) {
		int ret = this.dailyRealtimeMapper.updateSlotRealtimeStatistics(slotRealtime);
		return ret > 0;
	}

	public List<DataStatisticsTrendIndex> getIndexTrendStatisticsData(Long startTime, Long endTime) {
		return this.dailyRealtimeMapper.queryIndexTrendStatisticsData(startTime, endTime);
	}
	
	public List<ChannelDataStatisticsTrendIndex> getIndexTrendSspStatisticsData(Long startTime, Long endTime) {
		return this.dailyRealtimeMapper.queryIndexTrendSspStatisticsData(startTime, endTime);
	}

	public boolean updateSlotRealtimeIncome(DataStatisticsSlotRealtime dailyRealtime) {
		int ret = this.dailyRealtimeMapper.updateSlotRealtimeIncome(dailyRealtime);
		return ret > 0;
	}
	
	public boolean saveSlotRealtimeIncome(DataStatisticsSlotRealtime dailyRealtime) {
		int ret = this.dailyRealtimeMapper.saveSlotRealtimeIncome(dailyRealtime);
		return ret > 0;
	}

	public List<DataStatisticsSlotMonitoring> getSlotMonitoringStatisticsData(long startTime, long endTime) {
		return this.dailyRealtimeMapper.querySlotMonitoringStatisticsData(startTime, endTime);
	}
	
}
