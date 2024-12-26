package com.juan.adx.manage.action.adx;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.juan.adx.manage.service.adx.SspAdvertSlotAuditService;
import com.juan.adx.common.model.ManageResponse;
import com.juan.adx.common.model.PageData;
import com.juan.adx.common.validator.ParamAssert;
import com.juan.adx.model.enums.SspAuditStatus;
import com.juan.adx.model.form.manage.SspAdvertSlotForm;

@RestController
@RequestMapping("/adx/ssp/slotaudit")
public class SspAdvertSlotAuditAction {

	@Resource
	private SspAdvertSlotAuditService sspAdvertSlotAuditService;
	
	/**
	 * 查询待审核广告位列表
	 */
	@RequestMapping("/list")
	public ManageResponse list(SspAdvertSlotForm form) {
		if(StringUtils.isNotBlank(form.getName()) && StringUtils.isNumeric(form.getName())) {
			form.setSlotId(Integer.valueOf(form.getName()));
			form.setName(null);
		}
		PageData pageData = this.sspAdvertSlotAuditService.listSspAdvertSlotAudit(form);
		return new ManageResponse(pageData);
	}
	
	
	/**
	 * 更新待审核广告位审核状态
	 */
	@RequestMapping("/update/audit_status")
	public ManageResponse updateAuditStatus(Integer id, Integer auditStatus) {
		ParamAssert.isTrue(id == null || id <= 0, "广告ID不能为空");
		ParamAssert.isTrue(auditStatus == null || auditStatus <= 0, "审核状态不能为空");
		SspAuditStatus sspAuditStatus = SspAuditStatus.get(auditStatus);
		ParamAssert.isTrue(sspAuditStatus == null, "审核状态无效");
		ParamAssert.isTrue(sspAuditStatus != SspAuditStatus.FAIL_AUDIT && sspAuditStatus != SspAuditStatus.PASS_AUDIT, "审核状态不合法");
		this.sspAdvertSlotAuditService.updateAuditStatusAndSync(id, auditStatus);
		return new ManageResponse();
	}
}
