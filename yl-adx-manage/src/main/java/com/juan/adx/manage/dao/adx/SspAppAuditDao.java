package com.juan.adx.manage.dao.adx;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.juan.adx.manage.mapper.adx.SspAppAuditMapper;
import com.juan.adx.model.dto.manage.SspAppAuditDto;
import com.juan.adx.model.entity.manage.SspAppAudit;
import com.juan.adx.model.form.manage.SspAppForm;

@Repository
public class SspAppAuditDao {
	
	@Resource
	private SspAppAuditMapper sspAppAuditMapper;


	public List<SspAppAuditDto> querySspAppAuditList(SspAppForm form) {
		return this.sspAppAuditMapper.querySspAppAuditList(form);
	}
	
	public SspAppAudit querySspAppAudit(Integer id) {
		return this.sspAppAuditMapper.querySspAppAudit(id);
	}

	public int countAppAuditByPartnerId(Integer sspPartnerId) {
		return this.sspAppAuditMapper.countAppAuditByPartnerId(sspPartnerId);
	}

	public int saveSspAppAudit(SspAppAudit appAudit) {
		return this.sspAppAuditMapper.saveSspAppAudit(appAudit);
	}

	public int updateSspAppAudit(SspAppAudit appAudit) {
		return this.sspAppAuditMapper.updateSspAppAudit(appAudit);
	}

	public int deleteSspAppAudit(Integer id) {
		return this.sspAppAuditMapper.deleteSspAppAudit(id);
	}

	public int updateSspAppStatus(SspAppAudit appAudit) {
		return this.sspAppAuditMapper.updateSspAppStatus(appAudit);
	}


	
}
