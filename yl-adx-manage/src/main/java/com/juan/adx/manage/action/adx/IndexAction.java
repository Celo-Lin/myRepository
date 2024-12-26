package com.juan.adx.manage.action.adx;

import java.time.LocalDate;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.juan.adx.manage.filter.SessionKey;
import com.juan.adx.manage.service.adx.DataStatisticsIndexService;
import com.juan.adx.manage.service.permission.UserService;
import com.juan.adx.common.model.ManageResponse;
import com.juan.adx.common.utils.LocalDateUtils;
import com.juan.adx.common.validator.ParamAssert;
import com.juan.adx.common.validator.Validation;
import com.juan.adx.model.dto.manage.DataStatisticsIndexIncomeDto;
import com.juan.adx.model.dto.manage.DataStatisticsIndexTrendDto;
import com.juan.adx.model.enums.IndexTrendType;
import com.juan.adx.model.form.manage.IndexTrendDataForm;
import com.juan.adx.model.form.manage.UserUpdatePasswordForm;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/adx/index")
public class IndexAction {

	@Resource
	private DataStatisticsIndexService dataStatisticsIndexService;
	
	@Autowired
	private UserService userService;
	
	/**
	 * 首页收益数据
	 */
	@RequestMapping("/statistics")
	public ManageResponse statistics() {
		DataStatisticsIndexIncomeDto data = this.dataStatisticsIndexService.getIncomeData();
		return new ManageResponse(data);
	}
	
	/**
	 * 首页趋势数据
	 */
	@RequestMapping("/trend")
	public ManageResponse trend(IndexTrendDataForm form) {
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
    	IndexTrendType trendType = IndexTrendType.get(form.getTrendType());
    	ParamAssert.isTrue(trendType == null, "趋势图类型无效");
    	List<DataStatisticsIndexTrendDto> data  = this.dataStatisticsIndexService.getIndexTrendData(form);
		return new ManageResponse(data);
	}
	
	
	/**
	 * 修改登录密码
	 */
	@RequestMapping( "/update_passwd" )
	public ManageResponse updatePassword(UserUpdatePasswordForm form, @RequestParam(SessionKey.USER_ID) String userId) {
		ParamAssert.isBlank(userId, "用户无效");
		form.setUserId(userId);
		ParamAssert.isBlank(form.getOriginalPassword(), "原始密码不能为空");
		ParamAssert.isBlank(form.getNewPassword(), "新密码不能为空");
		ParamAssert.isBlank(form.getConfirmNewPassword(), "重复新密码不能为空");
		ParamAssert.isTrue(form.getNewPassword().equals(form.getOriginalPassword()), "新密码不能与原始密码相同");
		ParamAssert.isFalse(form.getNewPassword().equals(form.getConfirmNewPassword()), "新密码与重复新密码不相同");
		ParamAssert.isFalse(Validation.validate(form.getNewPassword(),Validation.PASSWD), "新密码格式错误,正确格式：6-20位，英文字母、数字、指定字符");
		form.setNewPassword(StringUtils.trimToEmpty(form.getNewPassword()));
		this.userService.updateUserPasswordByIndex(form);
		log.info( "index page update user password [{}] success", userId );
		return new ManageResponse();
	}
}
