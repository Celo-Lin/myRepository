package com.juan.adx.manage.dao.adx;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.juan.adx.manage.mapper.adx.DataStatisticsSlotDailyRealtimeMapper;
import com.juan.adx.model.dto.manage.DataStatisticsSlotDailyRealtimeDto;
import com.juan.adx.model.form.manage.DataStatisticsSlotDailyRealtimeForm;

@Repository
public class DataStatisticsSlotDailyRealtimeDao {

	@Resource
	private DataStatisticsSlotDailyRealtimeMapper dataStatisticsSlotDailyRealtimeMapper;

	public List<DataStatisticsSlotDailyRealtimeDto> queryDataStatementSlotDailyRealtimeList(
			DataStatisticsSlotDailyRealtimeForm form) {
		return this.dataStatisticsSlotDailyRealtimeMapper.queryDataStatementSlotDailyRealtimeList(form);
	}
}
