package com.juan.adx.manage.action.adx;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.juan.adx.manage.service.adx.DspPartnerService;
import com.juan.adx.common.model.ManageResponse;
import com.juan.adx.common.model.PageData;
import com.juan.adx.common.validator.ParamAssert;
import com.juan.adx.model.dto.manage.DspPartnerOptionDto;
import com.juan.adx.model.entity.manage.DspPartner;
import com.juan.adx.model.form.manage.DspPartnerForm;

@RestController
@RequestMapping("/adx/dsp/partner")
public class DspPartnerAction {

	@Resource
	private DspPartnerService dspPartnerService;
	
	/**
	 * 查询预算方列表
	 */
	@RequestMapping("/list")
	public ManageResponse list(DspPartnerForm form) {
		PageData pageData = this.dspPartnerService.listDspPartner(form);
		return new ManageResponse(pageData);
	}
	
	/**
	 * 根据预算方名称模糊查询预算方，用于筛选项
	 */
	@RequestMapping("/option")
	public ManageResponse option(String name) {
		List<DspPartnerOptionDto> data = this.dspPartnerService.listDspPartnerOption(name);
		return new ManageResponse(data);
	}
	
	
	/**
	 * 新增预算方
	 */
	@RequestMapping("/add")
	public ManageResponse add(@Validated DspPartner form) {
		ParamAssert.isBlank(form.getName(), "预算方名称不能为空");
		ParamAssert.isBlank(form.getCompany(), "公司名称不能为空");
		ParamAssert.isBlank(form.getContactName(), "联系人名称不能为空");
		ParamAssert.isBlank(form.getPhone(), "联系电话不能为空");
		this.dspPartnerService.addDspPartner(form);
		return new ManageResponse();
	}
	
	/**
	 * 预算方详情
	 */
	@RequestMapping("/detail")
	public ManageResponse detail(Integer id) {
		ParamAssert.isTrue(id == null || id <= 0, "预算方ID不能为空");
		DspPartner dspPartner = this.dspPartnerService.getDspPartner(id);
		return new ManageResponse(dspPartner);
	}
	
	
	/**
	 * 更新预算方信息
	 */
	@RequestMapping("/update")
	public ManageResponse update(@Validated DspPartner form) {
		ParamAssert.isTrue(form.getId() == null || form.getId() <= 0, "预算方ID不能为空");
		ParamAssert.isBlank(form.getContactName(), "联系人名称不能为空");
		ParamAssert.isBlank(form.getPhone(), "联系电话不能为空");
		this.dspPartnerService.updateDspPartner(form);
		return new ManageResponse();
	}
	
	/**
	 * 删除预算方
	 */
	@RequestMapping("/delete")
	public ManageResponse delete(Integer id) {
		ParamAssert.isTrue(id == null || id <= 0, "预算方ID不能为空");
		this.dspPartnerService.deleteDspPartner(id);
		return new ManageResponse();
	}
}
