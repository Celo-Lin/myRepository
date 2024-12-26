package com.juan.adx.manage.action.adx;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.juan.adx.manage.service.adx.DataStatisticsSlotMonitoringService;
import com.juan.adx.common.model.ManageResponse;
import com.juan.adx.common.model.PageData;
import com.juan.adx.model.form.manage.DataStatisticsSlotMonitoringForm;

/**
 * 广告位数据监控
 */
@RestController
@RequestMapping("/adx/statistics/slot_monitoring")
public class DataStatisticsSlotMonitoringAction {


	@Resource
	private DataStatisticsSlotMonitoringService dataStatisticsSlotMonitoringService;
	
	/**
	 * 查询广告位监控数据列表
	 */
	@RequestMapping("/list")
	public ManageResponse list(DataStatisticsSlotMonitoringForm form) {
		PageData pageData = this.dataStatisticsSlotMonitoringService.listSlotMonitoringData(form);
		return new ManageResponse(pageData);
	}
}
