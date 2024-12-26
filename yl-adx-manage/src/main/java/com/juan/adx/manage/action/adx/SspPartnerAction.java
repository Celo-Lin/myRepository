package com.juan.adx.manage.action.adx;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.juan.adx.manage.service.adx.SspPartnerService;
import com.juan.adx.common.model.ManageResponse;
import com.juan.adx.common.model.PageData;
import com.juan.adx.common.validator.ParamAssert;
import com.juan.adx.model.dto.manage.SspPartnerOptionDto;
import com.juan.adx.model.entity.manage.SspPartner;
import com.juan.adx.model.form.manage.SspPartnerForm;

@RestController
@RequestMapping("/adx/ssp/partner")
public class SspPartnerAction {
	
	@Resource
	private SspPartnerService sspPartnerService;

	/**
	 * 查询流量方列表
	 */
	@RequestMapping("/list")
	public ManageResponse list(SspPartnerForm form) {
		PageData pageData = this.sspPartnerService.listSspPartner(form);
		return new ManageResponse(pageData);
	}
	
	/**
	 * 根据流量方名称模糊查询流量方，用于筛选项
	 */
	@RequestMapping("/option")
	public ManageResponse option(String name) {
		List<SspPartnerOptionDto> data = this.sspPartnerService.listSspPartnerOption(name);
		return new ManageResponse(data);
	}
	
	
	/**
	 * 新增流量方
	 */
	@RequestMapping("/add")
	public ManageResponse add(@Validated SspPartner form) {
		ParamAssert.isBlank(form.getName(), "流量方名称不能为空");
		ParamAssert.isBlank(form.getCompany(), "公司名称不能为空");
		ParamAssert.isBlank(form.getContactName(), "联系人名称不能为空");
		ParamAssert.isBlank(form.getPhone(), "联系电话不能为空");
		this.sspPartnerService.addSspPartner(form);
		return new ManageResponse();
	}
	
	/**
	 * 流量方详情
	 */
	@RequestMapping("/detail")
	public ManageResponse detail(Integer id) {
		ParamAssert.isTrue(id == null || id <= 0, "流量方ID不能为空");
		SspPartner sspPartner = this.sspPartnerService.getSspPartner(id);
		return new ManageResponse(sspPartner);
	}
	
	
	/**
	 * 更新流量方信息
	 */
	@RequestMapping("/update")
	public ManageResponse update(SspPartner form) {
		ParamAssert.isTrue(form.getId() == null || form.getId() <= 0, "流量方ID不能为空");
		ParamAssert.isBlank(form.getContactName(), "联系人名称不能为空");
		ParamAssert.isBlank(form.getPhone(), "联系电话不能为空");
		this.sspPartnerService.updateSspPartner(form);
		return new ManageResponse();
	}
	
	/**
	 * 删除流量方
	 */
	@RequestMapping("/delete")
	public ManageResponse delete(Integer id) {
		ParamAssert.isTrue(id == null || id <= 0, "流量方ID不能为空");
		this.sspPartnerService.deleteSspPartner(id);
		return new ManageResponse();
	}
	
}

