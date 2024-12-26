package com.juan.adx.manage.action.adx;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.juan.adx.common.model.ManageResponse;
import com.juan.adx.common.model.PageData;
import com.juan.adx.common.validator.ParamAssert;
import com.juan.adx.manage.service.adx.SspAdvertSlotService;
import com.juan.adx.manage.service.adx.SspAppService;
import com.juan.adx.model.dto.manage.SspAdvertSlotDto;
import com.juan.adx.model.dto.manage.SspAppDto;
import com.juan.adx.model.entity.manage.SspAdvertSlot;
import com.juan.adx.model.enums.AdvertType;
import com.juan.adx.model.enums.CooperationMode;
import com.juan.adx.model.enums.IntegrationMode;
import com.juan.adx.model.form.manage.SspAdvertSlotForm;

@RestController
@RequestMapping("/adx/ssp/slot")
public class SspAdvertSlotAction {

	@Resource
	private SspAdvertSlotService sspAdvertSlotService;

	@Resource
	private SspAppService sspAppService;
	
	
	/**
	 * 查询广告位列表
	 */
	@RequestMapping("/list")
	public ManageResponse list(SspAdvertSlotForm form) {
		if(StringUtils.isNotBlank(form.getName()) && StringUtils.isNumeric(form.getName())) {
			form.setSlotId(Integer.valueOf(form.getName()));
			form.setName(null);
		}
		PageData pageData = this.sspAdvertSlotService.listSspAdvertSlot(form);
		return new ManageResponse(pageData);
	}
	
	/**
	 * 新增广告位
	 */
	@RequestMapping("/add")
	public ManageResponse add(SspAdvertSlot form) {
		ParamAssert.isTrue(form.getSspPartnerId() == null || form.getSspPartnerId().intValue() <= 0, "流量方不能为空");
		ParamAssert.isBlank(form.getName(), "广告位名称不能为空");
		ParamAssert.isTrue(form.getAppId() == null || form.getAppId() <= 0, "应用不能为空");
		ParamAssert.isTrue(form.getType() == null || form.getType() <= 0, "广告类型不能为空");
		AdvertType advertType = AdvertType.get(form.getType());
		ParamAssert.isTrue(advertType == null , "广告类型无效");
		ParamAssert.isTrue(form.getIntegrationMode() == null || form.getIntegrationMode() <= 0, "接入方式不能为空");
		IntegrationMode integrationMode = IntegrationMode.get(form.getIntegrationMode());
		ParamAssert.isTrue(integrationMode == null , "接入方式无效");
		ParamAssert.isTrue(form.getCooperationMode() == null || form.getCooperationMode() <= 0, "合作方式不能为空");
		CooperationMode cooperationMode = CooperationMode.get(form.getCooperationMode());
		ParamAssert.isTrue(cooperationMode == null , "合作方式无效");
		form.setRemarks(StringUtils.trimToEmpty(form.getRemarks()));
		ParamAssert.isTrue(form.getSspBidPrice() == null || form.getType() < 0, "媒体底价不能为空");
		
		SspAppDto sspAppDto = this.sspAppService.getSspApp(form.getAppId());
		ParamAssert.isTrue(sspAppDto == null , "应用ID不存在");
		ParamAssert.isTrue(sspAppDto.getSspPartnerId().intValue() != form.getSspPartnerId().intValue() , "应用ID与流量方ID不匹配");
		
		this.sspAdvertSlotService.addSspAdvertSlotAndSyncSlotAudit(form);
		return new ManageResponse();
	}
	
	/**
	 * 广告位详情
	 */
	@RequestMapping("/detail")
	public ManageResponse detail(Integer id) {
		ParamAssert.isTrue(id == null || id <= 0, "广告位ID不能为空");
		SspAdvertSlotDto advertSlotDto = this.sspAdvertSlotService.getSspAdvertSlot(id);
		return new ManageResponse(advertSlotDto);
	}
	
	/**
	 * 更新广告位信息
	 */
	@RequestMapping("/update")
	public ManageResponse update(SspAdvertSlot form) {
		ParamAssert.isTrue(form.getId() == null || form.getId().intValue() <= 0, "广告位不能为空");
		ParamAssert.isBlank(form.getName(), "广告位名称不能为空");
		ParamAssert.isTrue(form.getType() == null || form.getType() <= 0, "广告类型不能为空");
		AdvertType advertType = AdvertType.get(form.getType());
		ParamAssert.isTrue(advertType == null , "广告类型无效");
		ParamAssert.isTrue(form.getIntegrationMode() == null || form.getIntegrationMode() <= 0, "接入方式不能为空");
		IntegrationMode integrationMode = IntegrationMode.get(form.getIntegrationMode());
		ParamAssert.isTrue(integrationMode == null , "接入方式无效");
		ParamAssert.isTrue(form.getCooperationMode() == null || form.getCooperationMode() <= 0, "合作方式不能为空");
		CooperationMode cooperationMode = CooperationMode.get(form.getCooperationMode());
		ParamAssert.isTrue(cooperationMode == null , "合作方式无效");
		ParamAssert.isTrue(form.getSspBidPrice() == null || form.getType() < 0, "媒体底价不能为空");
		
		form.setRemarks(StringUtils.trimToEmpty(form.getRemarks()));
		this.sspAdvertSlotService.updateSspAdvertSlotAndSyncSlotAudit(form);
		return new ManageResponse();
	}
	
	/**
	 * 更新广告位状态
	 */
	@RequestMapping("/update/status")
	public ManageResponse updateStatus(Integer id, Integer status) {
		ParamAssert.isTrue(id == null || id <= 0, "广告位ID不能为空");
		ParamAssert.isTrue(status == null || status <= 0, "广告位状态不能为空");
		this.sspAdvertSlotService.updateSspAdvertSlotStatus(id, status);
		return new ManageResponse();
	}
	
	/**
	 * 删除广告位
	 */
	@RequestMapping("/delete")
	public ManageResponse delete(Integer id) {
		ParamAssert.isTrue(id == null || id <= 0, "广告位ID不能为空");
		this.sspAdvertSlotService.deleteSspAdvertSlot(id);
		return new ManageResponse();
	}
}
