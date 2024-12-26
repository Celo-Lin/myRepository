package com.juan.adx.manage.dao.adx;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.juan.adx.manage.mapper.adx.DataStatisticsSlotMonitoringMapper;
import com.juan.adx.model.dto.manage.DataStatisticsSlotMonitoringDto;
import com.juan.adx.model.form.manage.DataStatisticsSlotMonitoringForm;

@Repository
public class DataStatisticsSlotMonitoringDao {

	@Resource
	private DataStatisticsSlotMonitoringMapper monitoringMapper;

	public List<DataStatisticsSlotMonitoringDto> querySlotMonitoringDataList(DataStatisticsSlotMonitoringForm form) {
		return this.monitoringMapper.querySlotMonitoringDataList(form);
	}
	
	
}
