package com.juan.adx.manage.dao.adx;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.juan.adx.manage.mapper.adx.SspAdvertSlotAuditMapper;
import com.juan.adx.model.dto.manage.SspAdvertSlotAuditDto;
import com.juan.adx.model.entity.manage.SspAdvertSlotAudit;
import com.juan.adx.model.form.manage.SspAdvertSlotForm;

@Repository
public class SspAdvertSlotAuditDao {

	@Resource
	private SspAdvertSlotAuditMapper sspAdvertSlotAuditMapper;
	

	public List<SspAdvertSlotAuditDto> querySspAdvertSlotAuditList(SspAdvertSlotForm form) {
		return this.sspAdvertSlotAuditMapper.querySspAdvertSlotAuditList(form);
	}

	public SspAdvertSlotAudit querySspAdvertSlotAudit(Integer id) {
		return this.sspAdvertSlotAuditMapper.querySspAdvertSlotAudit(id);
	}

	public int saveSspSlotAudit(SspAdvertSlotAudit slotAudit) {
		return this.sspAdvertSlotAuditMapper.saveSspSlotAudit(slotAudit);
	}

	public int updateSspSlotAudit(SspAdvertSlotAudit slotAudit) {
		return this.sspAdvertSlotAuditMapper.updateSspSlotAudit(slotAudit);
	}

	public int updateSspAppStatus(SspAdvertSlotAudit updateSlotAudit) {
		return this.sspAdvertSlotAuditMapper.updateAuditStatus(updateSlotAudit);
	}

	public Integer queryMaxSlotId(Integer appId) {
		return this.sspAdvertSlotAuditMapper.queryMaxSlotId(appId);
	}


}
