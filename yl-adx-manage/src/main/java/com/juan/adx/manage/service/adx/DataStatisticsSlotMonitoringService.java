package com.juan.adx.manage.service.adx;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.juan.adx.manage.dao.adx.DataStatisticsSlotMonitoringDao;
import com.juan.adx.common.model.PageData;
import com.juan.adx.common.utils.LocalDateUtils;
import com.juan.adx.common.utils.PriceUtil;
import com.juan.adx.model.dto.manage.DataStatisticsSlotMonitoringDto;
import com.juan.adx.model.form.manage.DataStatisticsSlotMonitoringForm;

@Service
public class DataStatisticsSlotMonitoringService {
	
	@Resource
	private DataStatisticsSlotMonitoringDao monitoringDao;

	public PageData listSlotMonitoringData(DataStatisticsSlotMonitoringForm form) {
		form.setStartTime(LocalDateUtils.getToDayStartSeconds());
		form.setEndTime(LocalDateUtils.getToDayStartSeconds());
		PageHelper.startPage(form.getPageNo(), form.getPageSize());
		List<DataStatisticsSlotMonitoringDto> dataList = this.monitoringDao.querySlotMonitoringDataList(form);
		for (DataStatisticsSlotMonitoringDto dataStatisticsSlot : dataList) {
			this.calcRatioAndEcpm(dataStatisticsSlot);
		}
		PageInfo<DataStatisticsSlotMonitoringDto> pageInfo = new PageInfo<DataStatisticsSlotMonitoringDto>(dataList);
		return new PageData().addPageInfo(pageInfo, dataList);	
	}

	private void calcRatioAndEcpm(DataStatisticsSlotMonitoringDto dataStatistics) {
		
		double fillRatio = PriceUtil.calcRatio(dataStatistics.getFillCount(), dataStatistics.getRequestCount());
		
		double displayRatio = PriceUtil.calcRatio(dataStatistics.getDisplayCount(), dataStatistics.getFillCount());
		
		double clickRatio = PriceUtil.calcRatio(dataStatistics.getClickCount(), dataStatistics.getDisplayCount());
		
		dataStatistics.setFillRatio(fillRatio);
		dataStatistics.setDisplayRatio(displayRatio);
		dataStatistics.setClickRatio(clickRatio);
	}
}
