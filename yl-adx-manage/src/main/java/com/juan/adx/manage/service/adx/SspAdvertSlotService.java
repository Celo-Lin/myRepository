package com.juan.adx.manage.service.adx;

import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.juan.adx.manage.dao.adx.SspAdvertSlotAuditDao;
import com.juan.adx.manage.dao.adx.SspAdvertSlotDao;
import com.juan.adx.manage.dao.adx.SspAppAuditDao;
import com.juan.adx.common.exception.ExceptionEnum;
import com.juan.adx.common.exception.ServiceRuntimeException;
import com.juan.adx.common.model.PageData;
import com.juan.adx.common.utils.LocalDateUtils;
import com.juan.adx.model.constants.ManageCommonConstants;
import com.juan.adx.model.dto.manage.SspAdvertSlotDto;
import com.juan.adx.model.entity.manage.SspAdvertSlot;
import com.juan.adx.model.entity.manage.SspAdvertSlotAudit;
import com.juan.adx.model.entity.manage.SspAppAudit;
import com.juan.adx.model.enums.SspAuditStatus;
import com.juan.adx.model.enums.Status;
import com.juan.adx.model.form.manage.SspAdvertSlotForm;

@Service
public class SspAdvertSlotService {

	@Resource
	private SspAdvertSlotDao sspAdvertSlotDao;
	
	@Resource
	private SspAdvertSlotAuditDao sspAdvertSlotAuditDao;
	
	@Resource
	private SspAppAuditDao sspAppAuditDao;
	
	@Resource
	private SlotBudgetService slotBudgetService;
	
	
	public boolean existSlotByAppid(Integer appId) {
		int count = this.sspAdvertSlotDao.countSlotByAppid(appId);
		return count > 0;
	}

	public PageData listSspAdvertSlot(SspAdvertSlotForm form) {
		PageHelper.startPage(form.getPageNo(), form.getPageSize());
        List<SspAdvertSlotDto> dataList = this.sspAdvertSlotDao.querySspAdvertSlotList(form);
        PageInfo<SspAdvertSlotDto> pageInfo = new PageInfo<SspAdvertSlotDto>(dataList);
		return new PageData().addPageInfo(pageInfo, dataList);	
	}
	
	private int generateSlotId(Integer appId) {
		Integer maxSlotId = this.sspAdvertSlotAuditDao.queryMaxSlotId(appId);
		if(maxSlotId != null && maxSlotId.intValue() > 0) {
			return ++maxSlotId;
		}
		SspAppAudit sspAppAudit = this.sspAppAuditDao.querySspAppAudit(appId);
		maxSlotId = sspAppAudit.getId() * ManageCommonConstants.ID_STEP_SIZE + 1;
		return maxSlotId;
	}

	@Transactional(value = "adxTransactionManager")
	public boolean addSspAdvertSlotAndSyncSlotAudit(SspAdvertSlot advertSlot) {
		long nowSeconds = LocalDateUtils.getNowSeconds();
		//保存待审核表数据
		SspAdvertSlotAudit slotAudit = new SspAdvertSlotAudit();
		Integer slotId = this.generateSlotId(advertSlot.getAppId());
		slotAudit.setId(slotId);
		slotAudit.setSspPartnerId(advertSlot.getSspPartnerId());
		slotAudit.setAppId(advertSlot.getAppId());
		slotAudit.setName(advertSlot.getName());
		slotAudit.setType(advertSlot.getType());
		slotAudit.setIntegrationMode(advertSlot.getIntegrationMode());
		slotAudit.setRemarks(advertSlot.getRemarks());
		slotAudit.setAuditStatus(SspAuditStatus.PASS_AUDIT.getStatus());
		slotAudit.setAuditComments(StringUtils.EMPTY);
		slotAudit.setCtime(nowSeconds);
		slotAudit.setUtime(nowSeconds);
		this.sspAdvertSlotAuditDao.saveSspSlotAudit(slotAudit);
		
		//保存已审核表数据
		advertSlot.setId(slotId);
		advertSlot.setStatus(Status.VALID.getStatus());
		advertSlot.setCtime(nowSeconds);
		advertSlot.setUtime(nowSeconds);
		advertSlot.setSspBidPrice(Optional.ofNullable(advertSlot.getSspBidPrice()).orElse(0));
		int ret = this.sspAdvertSlotDao.saveSspAdvertSlot(advertSlot);
		return ret > 0;
	}

	public SspAdvertSlotDto getSspAdvertSlot(Integer id) {
		return this.sspAdvertSlotDao.querySspAdvertSlot(id);
	}

	@Transactional(value = "adxTransactionManager")
	public boolean updateSspAdvertSlotAndSyncSlotAudit(SspAdvertSlot advertSlot) {
		long nowSeconds = LocalDateUtils.getNowSeconds();
		advertSlot.setUtime(nowSeconds);
		advertSlot.setSspBidPrice(Optional.ofNullable(advertSlot.getSspBidPrice()).orElse(0));
		this.sspAdvertSlotDao.updateSspAdvertSlot(advertSlot);
		
		SspAdvertSlotAudit slotAudit = new SspAdvertSlotAudit();
		slotAudit.setId(advertSlot.getId());
		slotAudit.setName(advertSlot.getName());
		slotAudit.setType(advertSlot.getType());
		slotAudit.setIntegrationMode(advertSlot.getIntegrationMode());
		slotAudit.setRemarks(advertSlot.getRemarks());
		slotAudit.setCtime(nowSeconds);
		slotAudit.setUtime(nowSeconds);
		this.sspAdvertSlotAuditDao.updateSspSlotAudit(slotAudit);
		return true;
	}

	public boolean updateSspAdvertSlotStatus(Integer id, Integer status) {
		int ret = this.sspAdvertSlotDao.updateSspAdvertSlotStatus(id, status);
		return ret > 0;
	}

	public boolean deleteSspAdvertSlot(Integer id) {
		boolean existMappingBySlotId = this.slotBudgetService.existMappingBySlotId(id);
		if(existMappingBySlotId) {
			throw new ServiceRuntimeException(ExceptionEnum.MUST_DELETE_SLOT_BUDGET);
		}
		int ret = this.sspAdvertSlotDao.deleteSspAdvertSlot(id);
		return ret > 0;
	}
	
}
