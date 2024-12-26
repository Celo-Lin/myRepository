package com.juan.adx.channel.action;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.juan.adx.channel.filter.SessionKey;
import com.juan.adx.channel.service.ChannelAdvertSlotAuditService;
import com.juan.adx.channel.service.ChannelAppAuditService;
import com.juan.adx.common.model.ManageResponse;
import com.juan.adx.common.model.PageData;
import com.juan.adx.common.validator.ParamAssert;
import com.juan.adx.model.dto.sspmanage.ChannelAdvertSlotAuditDto;
import com.juan.adx.model.dto.sspmanage.ChannelAppAuditDto;
import com.juan.adx.model.entity.manage.SspAdvertSlotAudit;
import com.juan.adx.model.enums.AdvertType;
import com.juan.adx.model.enums.IntegrationMode;
import com.juan.adx.model.form.sspmanage.ChannelAdvertSlotForm;

@RestController
@RequestMapping("/slot")
public class ChannelAdvertSlotAuditAction extends BaseAction {

	@Resource
	private ChannelAdvertSlotAuditService channelAdvertSlotAuditService;
	
	@Resource
	private ChannelAppAuditService channelAppAuditService;
	
	/**
	 * 查询流量方广告位列表
	 */
	@RequestMapping("/list")
	public ManageResponse list(@RequestParam(SessionKey.SSP_PARTNER_ID) String sspPartnerIdStr, ChannelAdvertSlotForm form) {
		Integer sspPartnerId = this.getSspPartnerId(sspPartnerIdStr);
		form.setSspPartnerId(sspPartnerId);
		PageData pageData = this.channelAdvertSlotAuditService.listSspAdvertSlotAudit(form);
		return new ManageResponse(pageData);
	}
	
	
	/**
	 * 流量方广告位详情
	 */
	@RequestMapping("/detail")
	public ManageResponse detail(@RequestParam(SessionKey.SSP_PARTNER_ID) String sspPartnerIdStr, Integer id) {
		ParamAssert.isTrue(id == null || id <= 0, "广告位ID不能为空");
		Integer sspPartnerId = this.getSspPartnerId(sspPartnerIdStr);
		ChannelAdvertSlotAuditDto advertSlotAuditDto = this.channelAdvertSlotAuditService.getSspAdvertSlotAudit(sspPartnerId, id);
		return new ManageResponse(advertSlotAuditDto);
	}
	
	/**
	 * 新增流量方广告位
	 */
	@RequestMapping("/add")
	public ManageResponse add(@RequestParam(SessionKey.SSP_PARTNER_ID) String sspPartnerIdStr, SspAdvertSlotAudit form) {
		Integer sspPartnerId = this.getSspPartnerId(sspPartnerIdStr);
		form.setSspPartnerId(sspPartnerId);
		ParamAssert.isBlank(form.getName(), "广告位名称不能为空");
		ParamAssert.isTrue(form.getAppId() == null || form.getAppId() <= 0, "应用不能为空");
		ParamAssert.isTrue(form.getType() == null || form.getType() <= 0, "广告类型不能为空");
		AdvertType advertType = AdvertType.get(form.getType());
		ParamAssert.isTrue(advertType == null , "广告类型无效");
		ParamAssert.isTrue(form.getIntegrationMode() == null || form.getIntegrationMode() <= 0, "接入方式不能为空");
		IntegrationMode integrationMode = IntegrationMode.get(form.getIntegrationMode());
		ParamAssert.isTrue(integrationMode == null , "接入方式无效");
		form.setRemarks(StringUtils.trimToEmpty(form.getRemarks()));
		
		ChannelAppAuditDto appAuditDto = this.channelAppAuditService.getSspAppAudit(sspPartnerId, form.getAppId());
		ParamAssert.isTrue(appAuditDto == null , "应用ID不存在");
		ParamAssert.isTrue(appAuditDto.getSspPartnerId().intValue() != form.getSspPartnerId().intValue() , "应用ID与流量方ID不匹配");
		
		this.channelAdvertSlotAuditService.saveSspAdvertSlotAudit(form);
		return new ManageResponse();
	}
	
	
	
	/**
	 * 更新流量方广告位信息
	 */
	@RequestMapping("/update")
	public ManageResponse update(@RequestParam(SessionKey.SSP_PARTNER_ID) String sspPartnerIdStr, SspAdvertSlotAudit form) {
		Integer sspPartnerId = this.getSspPartnerId(sspPartnerIdStr);
		form.setSspPartnerId(sspPartnerId);
		ParamAssert.isBlank(form.getName(), "广告位名称不能为空");
		ParamAssert.isTrue(form.getType() == null || form.getType() <= 0, "广告类型不能为空");
		AdvertType advertType = AdvertType.get(form.getType());
		ParamAssert.isTrue(advertType == null , "广告类型无效");
		ParamAssert.isTrue(form.getIntegrationMode() == null || form.getIntegrationMode() <= 0, "接入方式不能为空");
		IntegrationMode integrationMode = IntegrationMode.get(form.getIntegrationMode());
		ParamAssert.isTrue(integrationMode == null , "接入方式无效");
		form.setRemarks(StringUtils.trimToEmpty(form.getRemarks()));
		
		this.channelAdvertSlotAuditService.updateSspAdvertSlotAudit(form);
		return new ManageResponse();
	}
	
}
