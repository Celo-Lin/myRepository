package com.juan.adx.manage.service.adx;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.juan.adx.manage.dao.adx.SspAdvertSlotAuditDao;
import com.juan.adx.manage.dao.adx.SspAdvertSlotDao;
import com.juan.adx.common.exception.ExceptionEnum;
import com.juan.adx.common.exception.ServiceRuntimeException;
import com.juan.adx.common.model.PageData;
import com.juan.adx.common.utils.LocalDateUtils;
import com.juan.adx.common.validator.ParamAssert;
import com.juan.adx.model.dto.manage.SspAdvertSlotAuditDto;
import com.juan.adx.model.entity.manage.SspAdvertSlot;
import com.juan.adx.model.entity.manage.SspAdvertSlotAudit;
import com.juan.adx.model.enums.CooperationMode;
import com.juan.adx.model.enums.SspAuditStatus;
import com.juan.adx.model.enums.Status;
import com.juan.adx.model.form.manage.SspAdvertSlotForm;

@Service
public class SspAdvertSlotAuditService {

	@Resource
	private SspAdvertSlotAuditDao sspAdvertSlotAuditDao;

	@Resource
	private SspAdvertSlotDao sspAdvertSlotDao;
	
	
	public PageData listSspAdvertSlotAudit(SspAdvertSlotForm form) {
		PageHelper.startPage(form.getPageNo(), form.getPageSize());
        List<SspAdvertSlotAuditDto> dataList = this.sspAdvertSlotAuditDao.querySspAdvertSlotAuditList(form);
        PageInfo<SspAdvertSlotAuditDto> pageInfo = new PageInfo<SspAdvertSlotAuditDto>(dataList);
		return new PageData().addPageInfo(pageInfo, dataList);	
	}

	@Transactional(value = "adxTransactionManager")
	public boolean updateAuditStatusAndSync(Integer id, Integer auditStatus) {
		SspAuditStatus auditStatusEnum = SspAuditStatus.get(auditStatus);
		ParamAssert.isTrue(auditStatusEnum == null, "待审核广告位状态无效");
		ParamAssert.isTrue(auditStatusEnum == SspAuditStatus.INSERT_UNAUDITED || auditStatusEnum == SspAuditStatus.UPDATE_UNAUDITED, "待审核广告位状态不合法");
		//检查当前审核的记录是否存在
		SspAdvertSlotAudit sspSlotAudit =  this.sspAdvertSlotAuditDao.querySspAdvertSlotAudit(id);
		if(sspSlotAudit == null) {
			throw new ServiceRuntimeException(ExceptionEnum.NOT_FOUND_SSP_SLOT_AUDIT);
		}
		SspAuditStatus existingAuditStatus = SspAuditStatus.get(sspSlotAudit.getAuditStatus());
		ParamAssert.isTrue(existingAuditStatus == SspAuditStatus.PASS_AUDIT || auditStatusEnum == SspAuditStatus.FAIL_AUDIT, "广告位已被审核，不能再次操作");
		//更新审核状态
		long nowSeconds = LocalDateUtils.getNowSeconds();
		SspAdvertSlotAudit updateSlotAudit = new SspAdvertSlotAudit();
		updateSlotAudit.setId(id);
		updateSlotAudit.setAuditStatus(auditStatus);
		updateSlotAudit.setUtime(nowSeconds);
		boolean ret = this.sspAdvertSlotAuditDao.updateSspAppStatus(updateSlotAudit) > 0;
		if(auditStatusEnum != SspAuditStatus.PASS_AUDIT) {
			return ret;
		}
		
		//将审核通过的广告位同步到已审核表
		SspAdvertSlot sspSlot = new SspAdvertSlot();
		sspSlot.setId(sspSlotAudit.getId());
		sspSlot.setSspPartnerId(sspSlotAudit.getSspPartnerId());
		sspSlot.setAppId(sspSlotAudit.getAppId());
		sspSlot.setName(sspSlotAudit.getName());
		sspSlot.setType(sspSlotAudit.getType());
		sspSlot.setIntegrationMode(sspSlotAudit.getIntegrationMode());
		sspSlot.setCooperationMode(CooperationMode.PD.getMode());
		sspSlot.setStatus(Status.VALID.getStatus());
		sspSlot.setRemarks(StringUtils.EMPTY);
		sspSlot.setCtime(sspSlotAudit.getCtime());
		sspSlot.setUtime(nowSeconds);
		int updateSspAppRet = this.sspAdvertSlotDao.syncChannelUpdateToSspAdvertSlot(sspSlot);
		if(updateSspAppRet > 0) {
			return true;
		}
		int saveSspAppRet = this.sspAdvertSlotDao.saveSspAdvertSlot(sspSlot);
		return saveSspAppRet > 0;
	}
}
