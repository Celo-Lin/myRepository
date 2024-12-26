package com.juan.adx.manage.action.adx;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.juan.adx.manage.service.adx.SspAppService;
import com.juan.adx.common.model.ManageResponse;
import com.juan.adx.common.model.PageData;
import com.juan.adx.common.validator.ParamAssert;
import com.juan.adx.model.dto.manage.SspAppDto;
import com.juan.adx.model.dto.manage.SspAppOptionDto;
import com.juan.adx.model.entity.manage.SspApp;
import com.juan.adx.model.enums.AppSystemType;
import com.juan.adx.model.form.manage.SspAppForm;

@RestController
@RequestMapping("/adx/ssp/app")
public class SspAppAction {

	@Resource
	private SspAppService sspAppService;

	
	/**
	 * 查询应用列表
	 */
	@RequestMapping("/list")
	public ManageResponse list(SspAppForm form) {
		if(StringUtils.isNotBlank(form.getAppName()) && StringUtils.isNumeric(form.getAppName())) {
			form.setAppId(Integer.valueOf(form.getAppName()));
			form.setAppName(null);
		}
		PageData pageData = this.sspAppService.listSspApp(form);
		return new ManageResponse(pageData);
	}
	
	
	/**
	 * 根据应用名称模糊查询应用，用于筛选项
	 */
	@RequestMapping("/option")
	public ManageResponse option(String name) {
		List<SspAppOptionDto> data = this.sspAppService.listSspAppOption(name);
		return new ManageResponse(data);
	}
	
	/**
	 * 根据流量方ID查询应用，用于筛选项
	 */
	@RequestMapping("/select")
	public ManageResponse select(Integer sspPartnerId) {
		ParamAssert.isTrue(sspPartnerId == null || sspPartnerId <= 0, "流量方ID不能为空");
		List<SspAppOptionDto> data = this.sspAppService.listSspAppSelete(sspPartnerId);
		return new ManageResponse(data);
	}
	
	
	/**
	 * 新增应用
	 */
	@RequestMapping("/add")
	public ManageResponse add(SspApp form) {
		ParamAssert.isTrue(form.getSspPartnerId() == null || form.getSspPartnerId().intValue() <= 0, "流量方不能为空");
		ParamAssert.isBlank(form.getName(), "应用名称不能为空");
		ParamAssert.isBlank(form.getPackageName(), "应用包名不能为空");
		ParamAssert.isTrue(form.getSystemPlatform() == null || form.getSystemPlatform().intValue() <= 0, "应用系统不能为空");
		AppSystemType appSystemType = AppSystemType.get(form.getSystemPlatform());
		ParamAssert.isTrue(appSystemType == null, "应用系统无效");
		ParamAssert.isBlank(form.getDownloadUrl(), "下载链接不能为空");
		ParamAssert.isTrue(form.getIndustryId() == null || form.getIndustryId() <= 0, "应用行业不能为空");
		ParamAssert.isTrue(form.getAppStoreId() == null || form.getAppStoreId() <= 0, " 应用商店不能为空");
		this.sspAppService.addSspAppAndSyncAppAudit(form);
		return new ManageResponse();
	}
	
	/**
	 * 应用详情
	 */
	@RequestMapping("/detail")
	public ManageResponse detail(Integer id) {
		ParamAssert.isTrue(id == null || id <= 0, "应用ID不能为空");
		SspAppDto sspAppDto = this.sspAppService.getSspApp(id);
		return new ManageResponse(sspAppDto);
	}
	
	/**
	 * 更新应用信息
	 */
	@RequestMapping("/update")
	public ManageResponse update(SspApp form) {
		ParamAssert.isTrue(form.getId() == null || form.getId() <= 0, "应用ID不能为空");
		ParamAssert.isBlank(form.getName(), "应用名称不能为空");
		ParamAssert.isTrue(form.getSystemPlatform() == null || form.getSystemPlatform().intValue() <= 0, "应用系统不能为空");
		AppSystemType appSystemType = AppSystemType.get(form.getSystemPlatform());
		ParamAssert.isTrue(appSystemType == null, "应用系统无效");
		ParamAssert.isBlank(form.getDownloadUrl(), "下载链接不能为空");
		ParamAssert.isTrue(form.getIndustryId() == null || form.getIndustryId() <= 0, "应用行业不能为空");
		ParamAssert.isTrue(form.getAppStoreId() == null || form.getAppStoreId() <= 0, " 应用商店不能为空");
		this.sspAppService.updateSspAppSyncAppAudit(form);
		return new ManageResponse();
	}
	
	/**
	 * 更新应用状态
	 */
	@RequestMapping("/update/status")
	public ManageResponse updateStatus(Integer id, Integer status) {
		ParamAssert.isTrue(id == null || id <= 0, "应用ID不能为空");
		ParamAssert.isTrue(status == null || status <= 0, "应用状态不能为空");
		this.sspAppService.updateSspAppStatus(id, status);
		return new ManageResponse();
	}
	
	
	/**
	 * 删除应用
	 */
	@RequestMapping("/delete")
	public ManageResponse delete(Integer id) {
		ParamAssert.isTrue(id == null || id <= 0, "应用ID不能为空");
		this.sspAppService.deleteSspApp(id);
		return new ManageResponse();
	}
	
	
	
}
