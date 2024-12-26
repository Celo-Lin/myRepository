package com.juan.adx.manage.action.adx;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.juan.adx.common.exception.ExceptionEnum;
import com.juan.adx.common.exception.ServiceRuntimeException;
import com.juan.adx.common.model.ManageResponse;
import com.juan.adx.common.model.PageData;
import com.juan.adx.common.utils.LocalDateUtils;
import com.juan.adx.common.validator.ParamAssert;
import com.juan.adx.manage.service.adx.DataStatisticsSlotDailyRealtimeService;
import com.juan.adx.model.enums.CooperationModeSimple;
import com.juan.adx.model.form.manage.DataStatisticsSlotDailyRealtimeForm;

import lombok.extern.slf4j.Slf4j;

/**
 * 广告位时段数据统计报表接口
 */
@Slf4j
@RestController
@RequestMapping("/adx/statistics/slot_daily_realtime")
public class DataStatisticsSlotDailyRealtimeAction {

	@Resource
	private DataStatisticsSlotDailyRealtimeService dataStatisticsSlotDailyRealtimeService;
	
	/**
	 * 查询时段数据报表列表
	 */
	@RequestMapping("/list")
	public ManageResponse list(DataStatisticsSlotDailyRealtimeForm form) {
		ParamAssert.isTrue(form.getCooperationMode() == null || form.getCooperationMode().intValue() <= 0, "合作方式不能为空");
		CooperationModeSimple cooperationMode = CooperationModeSimple.get(form.getCooperationMode());
		ParamAssert.isTrue(cooperationMode == null , "合作方式无效");
		PageData pageData = this.dataStatisticsSlotDailyRealtimeService.listDataStatementSlotDailyRealtime(form);
		return new ManageResponse(pageData);
	}
	
	
	/**
	 * 导出时段数据报表列表
	 */
	@RequestMapping("/export")
    public void export(DataStatisticsSlotDailyRealtimeForm form, HttpServletResponse response, HttpServletRequest request){
		ParamAssert.isTrue(form.getCooperationMode() == null || form.getCooperationMode().intValue() <= 0, "合作方式不能为空");
		CooperationModeSimple cooperationMode = CooperationModeSimple.get(form.getCooperationMode());
		ParamAssert.isTrue(cooperationMode == null , "合作方式无效");
    	if( form.getStartTime() == null || form.getEndTime() == null ) {
    		throw new ServiceRuntimeException(ExceptionEnum.INVALID_PARAM.getCode(), "请输入导出时间，一次只允许导出31天内的数据");
    	}
    	Long durationDay = LocalDateUtils.getDurationDay(form.getStartTime(), form.getEndTime());
    	ParamAssert.isTrue(durationDay == null || durationDay.longValue() > 31, "一次只允许导出31天内的数据");
    	try {
    		Workbook workbook = dataStatisticsSlotDailyRealtimeService.exportStatisticsData(form);
    		response.setHeader("Content-Disposition", "attachment; filename=slot-daily-statistic-"+ LocalDateUtils.getNowMilliseconds() + ".xlsx");
    		response.setContentType("application/octet-stream; charset=utf-8");
            workbook.write(response.getOutputStream());
        } catch (Exception e) {
        	log.error("每日广告数据导出异常.", e);
        	throw new ServiceRuntimeException(ExceptionEnum.UNCLASSIFIED.getCode(), "数据报表导出失败，请联系管理员！");
        }
    }
	
	
}
