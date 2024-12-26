package com.juan.adx.channel.action;

import java.time.LocalDate;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.juan.adx.channel.filter.SessionKey;
import com.juan.adx.channel.service.ChannelDataStatisticsIndexService;
import com.juan.adx.channel.service.ChannelUserService;
import com.juan.adx.common.model.ManageResponse;
import com.juan.adx.common.utils.LocalDateUtils;
import com.juan.adx.common.validator.ParamAssert;
import com.juan.adx.common.validator.Validation;
import com.juan.adx.model.dto.sspmanage.ChannelDataStatisticsIndexIncomeDto;
import com.juan.adx.model.dto.sspmanage.ChannelDataStatisticsIndexTrendDto;
import com.juan.adx.model.enums.ChannelIndexTrendType;
import com.juan.adx.model.form.sspmanage.ChannelIndexTrendDataForm;
import com.juan.adx.model.form.sspmanage.ChannelUserUpdatePasswordForm;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/index")
public class ChannelIndexAction extends BaseAction {

	@Resource
	private ChannelDataStatisticsIndexService channelDataStatisticsIndexService;
	
	@Resource
	private ChannelUserService channelUserService;
	
	/**
	 * 首页收益数据
	 */
	@RequestMapping("/statistics")
	public ManageResponse statistics(@RequestParam(SessionKey.SSP_PARTNER_ID) String sspPartnerIdStr) {
		Integer sspPartnerId = this.getSspPartnerId(sspPartnerIdStr);
		ChannelDataStatisticsIndexIncomeDto data = this.channelDataStatisticsIndexService.getIncomeData(sspPartnerId);
		return new ManageResponse(data);
	}
	
	/**
	 * 首页趋势数据
	 */
	@RequestMapping("/trend")
	public ManageResponse trend(ChannelIndexTrendDataForm form, @RequestParam(SessionKey.SSP_PARTNER_ID) String sspPartnerIdStr) {
		Integer sspPartnerId = this.getSspPartnerId(sspPartnerIdStr);
		form.setSspPartnerId(sspPartnerId);
		if( form.getStartTime() == null || form.getEndTime() == null ) {
			LocalDate startLocalDate = LocalDateUtils.minusDays(1);
			LocalDate endLocalDate = LocalDateUtils.minusDays(7);
			long startTime = LocalDateUtils.getStartSecondsByLocalDate(startLocalDate);
			long endTime = LocalDateUtils.getStartSecondsByLocalDate(endLocalDate);
			form.setStartTime(startTime);
			form.setEndTime(endTime);
    	}
    	Long durationDay = LocalDateUtils.getDurationDay(form.getStartTime(), form.getEndTime());
    	ParamAssert.isTrue(durationDay == null || durationDay.longValue() > 31, "一次只允许导出31天内的数据");
    	ParamAssert.isTrue(form.getTrendType() == null || form.getTrendType().intValue() <= 0, "趋势图类型不能为空");
    	ChannelIndexTrendType trendType = ChannelIndexTrendType.get(form.getTrendType());
    	ParamAssert.isTrue(trendType == null, "趋势图类型无效");
    	List<ChannelDataStatisticsIndexTrendDto> data  = this.channelDataStatisticsIndexService.getIndexTrendData(form);
		return new ManageResponse(data);
	}
	
	/**
	 * 修改登录密码
	 */
	@RequestMapping( "/update_passwd" )
	public ManageResponse updatePassword(ChannelUserUpdatePasswordForm form, @RequestParam(SessionKey.USER_ID) String userIdStr, @RequestParam(SessionKey.SSP_PARTNER_ID) String sspPartnerIdStr) {
		Integer sspPartnerId = this.getSspPartnerId(sspPartnerIdStr);
		Integer userId = this.getUserId(userIdStr);
		form.setUserId(userId);
		form.setSspPartnerId(sspPartnerId);
		ParamAssert.isBlank(form.getOriginalPassword(), "原始密码不能为空");
		ParamAssert.isBlank(form.getNewPassword(), "新密码不能为空");
		ParamAssert.isBlank(form.getConfirmNewPassword(), "重复新密码不能为空");
		ParamAssert.isTrue(form.getNewPassword().equals(form.getOriginalPassword()), "新密码不能与原始密码相同");
		ParamAssert.isFalse(form.getNewPassword().equals(form.getConfirmNewPassword()), "新密码与重复新密码不相同");
		ParamAssert.isFalse(Validation.validate(form.getNewPassword(),Validation.PASSWD), "新密码格式错误,正确格式：6-20位，英文字母、数字、指定字符");
		form.setNewPassword(StringUtils.trimToEmpty(form.getNewPassword()));
		this.channelUserService.updateUserPasswordByIndex(form);
		log.info( "index page update user password [{}] success", userId );
		return new ManageResponse();
	}
}
