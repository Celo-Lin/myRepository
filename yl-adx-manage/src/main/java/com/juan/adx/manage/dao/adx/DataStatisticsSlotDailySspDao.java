package com.juan.adx.manage.dao.adx;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.juan.adx.manage.mapper.adx.DataStatisticsSlotDailySspMapper;
import com.juan.adx.model.dto.manage.DataStatisticsSlotDailySspDto;
import com.juan.adx.model.entity.manage.DataStatisticsSlotDailySsp;
import com.juan.adx.model.form.manage.DataStatisticsSlotDailySspForm;

@Repository
public class DataStatisticsSlotDailySspDao {
	
	@Resource
	private DataStatisticsSlotDailySspMapper dataStatisticsSlotDailySspMapper;

	public List<DataStatisticsSlotDailySspDto> queryDataStatementSlotDailySspList(DataStatisticsSlotDailySspForm form) {
		return this.dataStatisticsSlotDailySspMapper.queryDataStatementSlotDailySspList(form);
	}

	public int updateAuditStatus(Integer id, Integer status) {
		return this.dataStatisticsSlotDailySspMapper.updateAuditStatus(id, status);
	}

	public int updateDataStatementSlotDailySsp(DataStatisticsSlotDailySsp statisticsSlotDailySsp) {
		return this.dataStatisticsSlotDailySspMapper.updateDataStatementSlotDailySsp(statisticsSlotDailySsp);
	}

	public int updatePdSlotEstimateIncomeSsp(DataStatisticsSlotDailySsp statisticsSlotDailySsp) {
		return this.dataStatisticsSlotDailySspMapper.updatePdSlotEstimateIncomeSsp(statisticsSlotDailySsp);
	}

}
