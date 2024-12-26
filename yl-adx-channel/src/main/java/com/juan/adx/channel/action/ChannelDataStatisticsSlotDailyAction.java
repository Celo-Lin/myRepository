package com.juan.adx.channel.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.juan.adx.channel.filter.SessionKey;
import com.juan.adx.channel.service.ChannelDataStatisticsSlotDailyService;
import com.juan.adx.common.exception.ExceptionEnum;
import com.juan.adx.common.exception.ServiceRuntimeException;
import com.juan.adx.common.model.ManageResponse;
import com.juan.adx.common.model.PageData;
import com.juan.adx.common.utils.LocalDateUtils;
import com.juan.adx.common.validator.ParamAssert;
import com.juan.adx.model.form.sspmanage.ChannelDataStatisticsSlotDailyForm;

import lombok.extern.slf4j.Slf4j;

/**
 * 流量方广告位数据统计报表接口
 */
@Slf4j
@RestController
@RequestMapping("/statistics/slot_daily")
public class ChannelDataStatisticsSlotDailyAction extends BaseAction {

	@Resource
	private ChannelDataStatisticsSlotDailyService channelDataStatisticsSlotDailyService;

	
	/**
	 * 查询流量方数据报表列表
	 */
	@RequestMapping("/list")
	public ManageResponse list(ChannelDataStatisticsSlotDailyForm form, @RequestParam(SessionKey.SSP_PARTNER_ID) String sspPartnerIdStr) {
		Integer sspPartnerId = this.getSspPartnerId(sspPartnerIdStr);
		form.setSspPartnerId(sspPartnerId);
		PageData pageData = this.channelDataStatisticsSlotDailyService.listSspDataStatementSlotDaily(form);
		return new ManageResponse(pageData);
	}
	
	
	/**
	 * 导出流量方数据报表
	 */
	@RequestMapping("/export")
    public void export(ChannelDataStatisticsSlotDailyForm form, @RequestParam(SessionKey.SSP_PARTNER_ID) String sspPartnerIdStr, HttpServletResponse response, HttpServletRequest request){
		Integer sspPartnerId = this.getSspPartnerId(sspPartnerIdStr);
		form.setSspPartnerId(sspPartnerId);
		if( form.getStartTime() == null || form.getEndTime() == null ) {
    		throw new ServiceRuntimeException(ExceptionEnum.INVALID_PARAM.getCode(), "请输入导出时间，一次只允许导出31天内的数据");
    	}
    	Long durationDay = LocalDateUtils.getDurationDay(form.getStartTime(), form.getEndTime());
    	ParamAssert.isTrue(durationDay == null || durationDay.longValue() > 31, "一次只允许导出31天内的数据");
    	try {
    		Workbook workbook = channelDataStatisticsSlotDailyService.exportSspStatisticsData(form);
    		response.setHeader("Content-Disposition", "attachment; filename=ssp-data-statistic-"+ System.currentTimeMillis() + ".xlsx");
    		response.setContentType("application/octet-stream; charset=utf-8");
            workbook.write(response.getOutputStream());
        } catch (Exception e) {
        	log.error("ssp data statistic export excel data error.", e);
        	throw new ServiceRuntimeException(ExceptionEnum.UNCLASSIFIED.getCode(), "对不起，流量方数据报表导出失败，请联系管理员！");
        }
    }
	
	
	
}
