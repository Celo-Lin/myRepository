package com.juan.adx.api.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson2.JSON;
import com.juan.adx.api.config.ApiParameterConfig;
import com.juan.adx.api.service.ReportService;
import com.juan.adx.common.alg.base64.Base64Util;
import com.juan.adx.common.model.WebResponse;
import com.juan.adx.common.utils.LocalDateUtils;
import com.juan.adx.common.validator.ParamAssert;
import com.juan.adx.model.entity.api.ReportLinkParam;
import com.juan.adx.model.enums.MacroParameters;
import com.juan.adx.model.enums.ReportEventType;
import com.juan.adx.model.form.api.ReportEventForm;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/tracking")
public class ReportAction {

	@Resource
	private ReportService reportService;
	
	/**
	 * 广告位事件上报接口
	 * 上报事件：展示、点击、下载、安装、dp
	 */
	@RequestMapping( "/event/{eventType}" )
	public WebResponse click(@PathVariable("eventType") String eventType, ReportEventForm form, HttpServletResponse response, HttpServletRequest request) {
		if(ApiParameterConfig.printLogSspReportSwitch) {
			//打印SSP请求事件上报接口响应日志
			log.info("ssp report request, eventType:{} | requestParam:{}", eventType, JSON.toJSONString(form));
		}
		ParamAssert.isBlank(form.getInfo(), "info参数不能为空");
		ParamAssert.isTrue(form.getSlotId() == null || form.getSlotId().intValue() <= 0, "slotId参数不能为空");
		ParamAssert.isBlank(eventType, "eventType参数不能为空");
		ReportEventType eventTypeEnums = ReportEventType.get(eventType);
		ParamAssert.isTrue(eventTypeEnums == null, "eventType参数无效");
		if(StringUtils.equalsIgnoreCase(form.getTs(), MacroParameters.TS_S.getMacro()) || StringUtils.isBlank(form.getTs())) {
			form.setTs(String.valueOf(LocalDateUtils.getNowSeconds()));
		}
		String baseInfo = Base64Util.decodeWithKeyUrlSafe(form.getInfo(), ApiParameterConfig.base64EncryptKey);
		ReportLinkParam reportLinkParam = JSON.parseObject(baseInfo, ReportLinkParam.class);
		if(ApiParameterConfig.printLogSspReportSwitch) {
			//打印SSP请求事件上报接口响应日志
			log.info("ssp report request, decode param, eventType:{} | baseInfo:{}", eventType, baseInfo);
		}
		this.reportService.saveReportEvent(eventTypeEnums, reportLinkParam);
		return new WebResponse();
	}





}
