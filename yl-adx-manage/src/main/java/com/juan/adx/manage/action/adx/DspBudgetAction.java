package com.juan.adx.manage.action.adx;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.juan.adx.manage.service.adx.DspBudgetService;
import com.juan.adx.common.model.ManageResponse;
import com.juan.adx.common.model.PageData;
import com.juan.adx.common.validator.ParamAssert;
import com.juan.adx.model.dto.manage.DspBudgetDto;
import com.juan.adx.model.dto.manage.DspBudgetOptionDto;
import com.juan.adx.model.entity.manage.DspBudget;
import com.juan.adx.model.enums.CooperationModeSimple;
import com.juan.adx.model.form.manage.DspBudgetForm;

@RestController
@RequestMapping("/adx/dsp/budget")
public class DspBudgetAction {
	
	@Resource
	private DspBudgetService dspBudgetService;

	/**
	 * 查询预算列表
	 */
	@RequestMapping("/list")
	public ManageResponse list(DspBudgetForm form) {
		PageData pageData = this.dspBudgetService.listDspBudget(form);
		return new ManageResponse(pageData);
	}
	
	
	/**
	 * 根据预算方ID查询预算列表，用于筛选项
	 */
	@RequestMapping("/option")
	public ManageResponse option(Integer dspPartnerId) {
		ParamAssert.isTrue(dspPartnerId == null || dspPartnerId <= 0, "预算方ID不能为空");
		List<DspBudgetOptionDto> data = this.dspBudgetService.listDspBudgetOption(dspPartnerId);
		return new ManageResponse(data);
	}
	
	
	
	/**
	 * 新增预算
	 */
	@RequestMapping("/add")
	public ManageResponse add(DspBudget form) {
		ParamAssert.isTrue(form.getDspPartnerId() == null || form.getDspPartnerId().intValue() <= 0, "预算方不能为空");
		ParamAssert.isBlank(form.getName(), "预算名称不能为空");
		ParamAssert.isTrue(form.getCooperationMode() == null || form.getCooperationMode().intValue() <= 0, "合作方式不能为空");
		CooperationModeSimple cooperationMode = CooperationModeSimple.get(form.getCooperationMode());
		ParamAssert.isTrue(cooperationMode == null , "合作方式无效");
		ParamAssert.isTrue(form.getType() == null || form.getType().intValue() <= 0, "广告位类型不能为空");
		ParamAssert.isTrue(form.getDeviceMaxRequests() == null || form.getDeviceMaxRequests().intValue() < -1, "设备频率限制不能为空");
		form.setTitle(StringUtils.trimToEmpty(form.getTitle()));
		form.setDeeplink(StringUtils.trimToEmpty(form.getDeeplink()));
		form.setH5link(StringUtils.trimToEmpty(form.getH5link()));
		form.setDownloadUrl(StringUtils.trimToEmpty(form.getDownloadUrl()));
		form.setPictureUrl(StringUtils.trimToEmpty(form.getPictureUrl()));
		form.setVideoUrl(StringUtils.trimToEmpty(form.getVideoUrl()));
		this.dspBudgetService.addDspBudget(form);
		return new ManageResponse();
	}
	
	/**
	 * 预算详情
	 */
	@RequestMapping("/detail")
	public ManageResponse detail(Integer id) {
		ParamAssert.isTrue(id == null || id <= 0, "预算ID不能为空");
		DspBudgetDto dspBudgetDto = this.dspBudgetService.getDspBudget(id);
		return new ManageResponse(dspBudgetDto);
	}
	
	/**
	 * 更新预算信息
	 */
	@RequestMapping("/update")
	public ManageResponse update(DspBudget form) {
		ParamAssert.isTrue(form.getId() == null || form.getId().intValue() <= 0, "预算ID不能为空");
		ParamAssert.isTrue(form.getDspPartnerId() == null || form.getDspPartnerId().intValue() <= 0, "预算方不能为空");
		ParamAssert.isBlank(form.getName(), "预算名称不能为空");
		ParamAssert.isTrue(form.getCooperationMode() == null || form.getCooperationMode().intValue() <= 0, "合作方式不能为空");
		CooperationModeSimple cooperationMode = CooperationModeSimple.get(form.getCooperationMode());
		ParamAssert.isTrue(cooperationMode == null , "合作方式无效");
		ParamAssert.isTrue(form.getType() == null || form.getType().intValue() <= 0, "广告位类型不能为空");
		ParamAssert.isTrue(form.getDeviceMaxRequests() == null || form.getDeviceMaxRequests().intValue() < -1, "设备频率限制不能为空");
		form.setTitle(StringUtils.trimToEmpty(form.getTitle()));
		form.setDeeplink(StringUtils.trimToEmpty(form.getDeeplink()));
		form.setH5link(StringUtils.trimToEmpty(form.getH5link()));
		form.setDownloadUrl(StringUtils.trimToEmpty(form.getDownloadUrl()));
		form.setPictureUrl(StringUtils.trimToEmpty(form.getPictureUrl()));
		form.setVideoUrl(StringUtils.trimToEmpty(form.getVideoUrl()));
		this.dspBudgetService.updateDspBudget(form);
		return new ManageResponse();
	}
	
	/**
	 * 更新预算状态
	 */
	@RequestMapping("/update/status")
	public ManageResponse updateStatus(Integer id, Integer status) {
		ParamAssert.isTrue(id == null || id <= 0, "预算ID不能为空");
		ParamAssert.isTrue(status == null || status <= 0, "预算状态不能为空");
		this.dspBudgetService.updateDspBudgetStatus(id, status);
		return new ManageResponse();
	}
	
	/**
	 * 删除预算
	 */
	@RequestMapping("/delete")
	public ManageResponse delete(Integer id) {
		ParamAssert.isTrue(id == null || id <= 0, "预算ID不能为空");
		this.dspBudgetService.deleteDspBudget(id);
		return new ManageResponse();
	}
}
