package com.juan.adx.manage.service.adx;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
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
import com.juan.adx.model.dto.manage.SspAppDto;
import com.juan.adx.model.dto.manage.SspAppOptionDto;
import com.juan.adx.model.entity.manage.SspApp;
import com.juan.adx.model.entity.manage.SspAppAudit;
import com.juan.adx.model.enums.SspAuditStatus;
import com.juan.adx.model.enums.Status;
import com.juan.adx.model.form.manage.SspAppForm;


@Service
public class SspAppService {

	@Resource
	private SspAppDao sspAppDao;
	
	@Resource
	private SspAppAuditDao appAuditDao;
	
	@Resource
	private SspAdvertSlotService sspSlotService; 
	
	
	public boolean existAppByPartnerId(Integer sspPartnerId) {
		int count = this.appAuditDao.countAppAuditByPartnerId(sspPartnerId);
		return count > 0;
	}
	

	public PageData listSspApp(SspAppForm form) {
		PageHelper.startPage(form.getPageNo(), form.getPageSize());
        List<SspAppDto> dataList = this.sspAppDao.querySspApp(form);
        PageInfo<SspAppDto> pageInfo = new PageInfo<SspAppDto>(dataList);
		return new PageData().addPageInfo(pageInfo, dataList);	
	}


	@Transactional(value = "adxTransactionManager")
	public boolean addSspAppAndSyncAppAudit(SspApp app) {
		
		long nowSeconds = LocalDateUtils.getNowSeconds();
		//保存待审核表数据
		SspAppAudit appAudit = new SspAppAudit();
		appAudit.setSspPartnerId(app.getSspPartnerId());
		appAudit.setName(app.getName());
		appAudit.setPackageName(app.getPackageName());
		appAudit.setSystemPlatform(app.getSystemPlatform());
		appAudit.setIndustryId(app.getIndustryId());
		appAudit.setAppStoreId(app.getAppStoreId());
		appAudit.setDownloadUrl(app.getDownloadUrl());
		appAudit.setAuditStatus(SspAuditStatus.PASS_AUDIT.getStatus());
		appAudit.setAuditComments(StringUtils.EMPTY);
		appAudit.setCtime(nowSeconds);
		appAudit.setUtime(nowSeconds);
		this.appAuditDao.saveSspAppAudit(appAudit);
		//保存已审核表数据
		app.setId(appAudit.getId());
		app.setStatus(Status.VALID.getStatus());
		app.setCtime(nowSeconds);
		app.setUtime(nowSeconds);
		boolean ret = this.sspAppDao.saveSspApp(app) > 0;
		return ret;
	}


	public SspAppDto getSspApp(Integer id) {
		return this.sspAppDao.querySspApp(id);
	}


	@Transactional(value = "adxTransactionManager")
	public boolean updateSspAppSyncAppAudit(SspApp sspApp) {
		long nowSeconds = LocalDateUtils.getNowSeconds();
		sspApp.setUtime(nowSeconds);
		this.sspAppDao.updateSspApp(sspApp);
		
		SspAppAudit appAudit = new SspAppAudit();
		appAudit.setId(sspApp.getId());
		appAudit.setName(sspApp.getName());
		appAudit.setSystemPlatform(sspApp.getSystemPlatform());
		appAudit.setIndustryId(sspApp.getIndustryId());
		appAudit.setAppStoreId(sspApp.getAppStoreId());
		appAudit.setDownloadUrl(sspApp.getDownloadUrl());
		appAudit.setUtime(nowSeconds);
		this.appAuditDao.updateSspAppAudit(appAudit);
		return true;
	}


	public boolean updateSspAppStatus(Integer id, Integer status) {
		Status statusEnum = Status.get(status);
		ParamAssert.isTrue(statusEnum == null, "应用状态无效");
		return this.sspAppDao.updateSspAppStatus(id, status) > 0;
	}

	@Transactional(value = "adxTransactionManager")
	public boolean deleteSspApp(Integer id) {
		boolean existSlotByAppid = this.sspSlotService.existSlotByAppid(id);
		if(existSlotByAppid) {
			throw new ServiceRuntimeException(ExceptionEnum.MUST_DELETE_SSP_SLOT);
		}
		this.sspAppDao.deleteSspApp(id);
		this.appAuditDao.deleteSspAppAudit(id);
		return true;
	}


	public List<SspAppOptionDto> listSspAppOption(String name) {
		if(StringUtils.isBlank(name)) {
			return null;
		}
		return this.sspAppDao.querySspAppOption(name);
	}


	public List<SspAppOptionDto> listSspAppSelete(Integer sspPartnerId) {
		return this.sspAppDao.querySspAppSimpleBySspPartnerId(sspPartnerId);
	}
	
}
