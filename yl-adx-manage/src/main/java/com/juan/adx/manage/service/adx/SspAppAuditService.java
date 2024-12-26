package com.juan.adx.manage.service.adx;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.juan.adx.manage.dao.adx.SspAppAuditDao;
import com.juan.adx.manage.dao.adx.SspAppDao;
import com.juan.adx.common.exception.ExceptionEnum;
import com.juan.adx.common.exception.ServiceRuntimeException;
import com.juan.adx.common.model.PageData;
import com.juan.adx.common.utils.LocalDateUtils;
import com.juan.adx.common.validator.ParamAssert;
import com.juan.adx.model.dto.manage.SspAppAuditDto;
import com.juan.adx.model.entity.manage.SspApp;
import com.juan.adx.model.entity.manage.SspAppAudit;
import com.juan.adx.model.enums.SspAuditStatus;
import com.juan.adx.model.enums.Status;
import com.juan.adx.model.form.manage.SspAppForm;

@Service
public class SspAppAuditService {
	
	@Resource
	private SspAppAuditDao sspAppAuditDao;
	
	@Resource
	private SspAppDao sspAppDao;

	public PageData listSspAppAudit(SspAppForm form) {
		PageHelper.startPage(form.getPageNo(), form.getPageSize());
        List<SspAppAuditDto> dataList = this.sspAppAuditDao.querySspAppAuditList(form);
        PageInfo<SspAppAuditDto> pageInfo = new PageInfo<SspAppAuditDto>(dataList);
		return new PageData().addPageInfo(pageInfo, dataList);	
	}

	@Transactional(value = "adxTransactionManager")
	public boolean updateAuditStatusAndSync(Integer id, Integer auditStatus) {
		SspAuditStatus auditStatusEnum = SspAuditStatus.get(auditStatus);
		ParamAssert.isTrue(auditStatusEnum == null, "待审核应用状态无效");
		ParamAssert.isTrue(auditStatusEnum == SspAuditStatus.INSERT_UNAUDITED || auditStatusEnum == SspAuditStatus.UPDATE_UNAUDITED, "待审核应用状态不合法");
		//检查当前审核的记录是否存在
		SspAppAudit sspAppAudit =  this.sspAppAuditDao.querySspAppAudit(id);
		if(sspAppAudit == null) {
			throw new ServiceRuntimeException(ExceptionEnum.NOT_FOUND_SSP_APP_AUDIT);
		}
		SspAuditStatus existingAuditStatus = SspAuditStatus.get(sspAppAudit.getAuditStatus());
		ParamAssert.isTrue(existingAuditStatus == SspAuditStatus.PASS_AUDIT || auditStatusEnum == SspAuditStatus.FAIL_AUDIT, "应用已被审核，不能再次操作");
		
		//更新审核状态
		long nowSeconds = LocalDateUtils.getNowSeconds();
		SspAppAudit updateAppAudit = new SspAppAudit();
		updateAppAudit.setId(id);
		updateAppAudit.setAuditStatus(auditStatus);
		updateAppAudit.setUtime(nowSeconds);
		boolean ret = this.sspAppAuditDao.updateSspAppStatus(updateAppAudit) > 0;
		if(auditStatusEnum != SspAuditStatus.PASS_AUDIT) {
			return ret;
		}
		
		//将审核通过的应用同步到已审核应用表
		SspApp sspApp = new SspApp();
		sspApp.setId(sspAppAudit.getId());
		sspApp.setSspPartnerId(sspAppAudit.getSspPartnerId());
		sspApp.setName(sspAppAudit.getName());
		sspApp.setPackageName(sspAppAudit.getPackageName());
		sspApp.setSystemPlatform(sspAppAudit.getSystemPlatform());
		sspApp.setIndustryId(sspAppAudit.getIndustryId());
		sspApp.setAppStoreId(sspAppAudit.getAppStoreId());
		sspApp.setDownloadUrl(sspAppAudit.getDownloadUrl());
		sspApp.setStatus(Status.VALID.getStatus());
		sspApp.setCtime(sspAppAudit.getCtime());
		sspApp.setUtime(nowSeconds);
		int updateSspAppRet = this.sspAppDao.syncChannelUpdateToSspApp(sspApp);
		if(updateSspAppRet > 0) {
			return true;
		}
		int saveSspAppRet = this.sspAppDao.saveSspApp(sspApp);
		return saveSspAppRet > 0;
	}

}
