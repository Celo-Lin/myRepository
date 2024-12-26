package com.juan.adx.channel.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.juan.adx.channel.mapper.adx.ChannelAppAuditMapper;
import com.juan.adx.model.dto.sspmanage.ChannelAppAuditDto;
import com.juan.adx.model.dto.sspmanage.ChannelAppOptionDto;
import com.juan.adx.model.entity.manage.SspAppAudit;
import com.juan.adx.model.form.sspmanage.ChannelAppForm;

@Repository
public class ChannelAppAuditDao {

	@Resource
	private ChannelAppAuditMapper channelAppAuditMapper;

	public List<ChannelAppAuditDto> querySspAppAuditList(ChannelAppForm form) {
		return this.channelAppAuditMapper.querySspAppAuditList(form);
	}

	public int saveSspAppAudit(SspAppAudit appAudit) {
		return this.channelAppAuditMapper.saveSspAppAudit(appAudit);
	}

	public ChannelAppAuditDto querySspAppAudit(Integer sspPartnerId, Integer id) {
		return this.channelAppAuditMapper.querySspAppAudit(sspPartnerId, id);
	}

	public int updateSspAppAudit(SspAppAudit appAudit) {
		return this.channelAppAuditMapper.updateSspAppAudit(appAudit);
	}
	
	public List<ChannelAppOptionDto> querySspAppAuditOption(Integer sspPartnerId, String name) {
		return this.channelAppAuditMapper.querySspAppAuditOption(sspPartnerId, name);
	}



}
