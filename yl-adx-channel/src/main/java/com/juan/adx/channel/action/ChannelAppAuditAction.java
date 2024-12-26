package com.juan.adx.channel.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.juan.adx.channel.filter.SessionKey;
import com.juan.adx.channel.service.ChannelAppAuditService;
import com.juan.adx.common.model.ManageResponse;
import com.juan.adx.common.model.PageData;
import com.juan.adx.common.validator.ParamAssert;
import com.juan.adx.model.dto.sspmanage.ChannelAppAuditDto;
import com.juan.adx.model.dto.sspmanage.ChannelAppOptionDto;
import com.juan.adx.model.entity.manage.SspAppAudit;
import com.juan.adx.model.enums.AppSystemType;
import com.juan.adx.model.form.sspmanage.ChannelAppForm;

@RestController
@RequestMapping("/app")
public class ChannelAppAuditAction extends BaseAction {

	@Resource
	private ChannelAppAuditService channelAppAuditService;
	
	
	/**
	 * 查询流量方应用列表
	 */
	@RequestMapping("/list")
	public ManageResponse list(@RequestParam(SessionKey.SSP_PARTNER_ID) String sspPartnerIdStr, ChannelAppForm form) {
		Integer sspPartnerId = this.getSspPartnerId(sspPartnerIdStr);
		form.setSspPartnerId(sspPartnerId);
		PageData pageData = this.channelAppAuditService.listSspAppAudit(form);
		return new ManageResponse(pageData);
	}
	
	
	/**
	 * 根据应用名称模糊查询流量方应用，用于筛选项
	 */
	@RequestMapping("/option")
	public ManageResponse option(@RequestParam(SessionKey.SSP_PARTNER_ID) String sspPartnerIdStr, String name) {
		Integer sspPartnerId = this.getSspPartnerId(sspPartnerIdStr);
		List<ChannelAppOptionDto> data = this.channelAppAuditService.listSspAppAuditOption(sspPartnerId, name);
		return new ManageResponse(data);
	}
	

	/**
	 * 流量方应用详情
	 */
	@RequestMapping("/detail")
	public ManageResponse detail(@RequestParam(SessionKey.SSP_PARTNER_ID) String sspPartnerIdStr, Integer id) {
		ParamAssert.isTrue(id == null || id <= 0, "应用ID不能为空");
		Integer sspPartnerId = this.getSspPartnerId(sspPartnerIdStr);
		ChannelAppAuditDto sspAppDto = this.channelAppAuditService.getSspAppAudit(sspPartnerId, id);
		return new ManageResponse(sspAppDto);
	}
	
	
	/**
	 * 新增流量方应用
	 */
	@RequestMapping("/add")
	public ManageResponse add(SspAppAudit form, @RequestParam(SessionKey.SSP_PARTNER_ID) String sspPartnerIdStr) {
		Integer sspPartnerId = this.getSspPartnerId(sspPartnerIdStr);
		form.setSspPartnerId(sspPartnerId);
		ParamAssert.isBlank(form.getName(), "应用名称不能为空");
		ParamAssert.isBlank(form.getPackageName(), "应用包名不能为空");
		ParamAssert.isTrue(form.getSystemPlatform() == null || form.getSystemPlatform().intValue() <= 0, "应用系统不能为空");
		AppSystemType appSystemType = AppSystemType.get(form.getSystemPlatform());
		ParamAssert.isTrue(appSystemType == null, "应用系统无效");
		ParamAssert.isBlank(form.getDownloadUrl(), "下载链接不能为空");
		ParamAssert.isTrue(form.getIndustryId() == null || form.getIndustryId() <= 0, "应用行业不能为空");
		ParamAssert.isTrue(form.getAppStoreId() == null || form.getAppStoreId() <= 0, " 应用商店不能为空");
		this.channelAppAuditService.saveSspAppAudit(form);
		return new ManageResponse();
	}
	
	
	/**
	 * 更新流量方应用信息
	 */
	@RequestMapping("/update")
	public ManageResponse update(SspAppAudit form, @RequestParam(SessionKey.SSP_PARTNER_ID) String sspPartnerIdStr) {
		Integer sspPartnerId = this.getSspPartnerId(sspPartnerIdStr);
		form.setSspPartnerId(sspPartnerId);
		ParamAssert.isTrue(form.getId() == null || form.getId() <= 0, "应用ID不能为空");
		ParamAssert.isBlank(form.getName(), "应用名称不能为空");
		ParamAssert.isTrue(form.getSystemPlatform() == null || form.getSystemPlatform().intValue() <= 0, "应用系统不能为空");
		AppSystemType appSystemType = AppSystemType.get(form.getSystemPlatform());
		ParamAssert.isTrue(appSystemType == null, "应用系统无效");
		ParamAssert.isBlank(form.getDownloadUrl(), "下载链接不能为空");
		ParamAssert.isTrue(form.getIndustryId() == null || form.getIndustryId() <= 0, "应用行业不能为空");
		ParamAssert.isTrue(form.getAppStoreId() == null || form.getAppStoreId() <= 0, " 应用商店不能为空");
		this.channelAppAuditService.updateSspAppAudit(form);
		return new ManageResponse();
	}
	
	
	
}
