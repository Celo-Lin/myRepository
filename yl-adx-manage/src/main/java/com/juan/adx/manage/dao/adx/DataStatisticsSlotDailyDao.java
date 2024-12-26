package com.juan.adx.manage.dao.adx;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.juan.adx.manage.mapper.adx.DataStatisticsSlotDailyMapper;
import com.juan.adx.model.dto.manage.DataStatisticsSlotDailyDto;
import com.juan.adx.model.dto.manage.ImportSlotDailyDto;
import com.juan.adx.model.entity.manage.DataStatisticsSlotDaily;
import com.juan.adx.model.form.manage.DataStatisticsSlotDailyForm;

@Repository
public class DataStatisticsSlotDailyDao {

	@Resource
	private DataStatisticsSlotDailyMapper dataStatisticsSlotDailyMapper;

	public List<DataStatisticsSlotDailyDto> queryDataStatementSlotDailyList(DataStatisticsSlotDailyForm form) {
		return this.dataStatisticsSlotDailyMapper.queryDataStatementSlotDailyList(form);
	}

	public DataStatisticsSlotDailyDto queryDataStatementSlotDaily(Integer id) {
		return this.dataStatisticsSlotDailyMapper.queryDataStatementSlotDaily(id);
	}

	public int updateDataStatementSlotDaily(DataStatisticsSlotDaily slotDaily) {
		return this.dataStatisticsSlotDailyMapper.updateDataStatementSlotDaily(slotDaily);
	}

	public int updatePdSlotEstimateIncome(ImportSlotDailyDto slotDaily) {
		return this.dataStatisticsSlotDailyMapper.updatePdSlotEstimateIncome(slotDaily);
	}
	
}
