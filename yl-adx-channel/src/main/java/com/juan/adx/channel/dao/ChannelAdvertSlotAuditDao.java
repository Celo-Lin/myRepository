package com.juan.adx.channel.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.juan.adx.channel.mapper.adx.ChannelAdvertSlotAuditMapper;
import com.juan.adx.model.dto.sspmanage.ChannelAdvertSlotAuditDto;
import com.juan.adx.model.entity.manage.SspAdvertSlotAudit;
import com.juan.adx.model.form.sspmanage.ChannelAdvertSlotForm;

@Repository
public class ChannelAdvertSlotAuditDao {

	@Resource
	private ChannelAdvertSlotAuditMapper channelAdvertSlotAuditMapper;
	

	public List<ChannelAdvertSlotAuditDto> querySspAdvertSlotAuditList(ChannelAdvertSlotForm form) {
		return this.channelAdvertSlotAuditMapper.querySspAdvertSlotAuditList(form);
	}

	public ChannelAdvertSlotAuditDto querySspAdvertSlotAudit(Integer sspPartnerId, Integer id) {
		return this.channelAdvertSlotAuditMapper.querySspAdvertSlotAudit(sspPartnerId, id);
	}

	public int saveSspAdvertSlotAudit(SspAdvertSlotAudit slotAudit) {
		return this.channelAdvertSlotAuditMapper.saveSspAdvertSlotAudit(slotAudit);
	}

	public int updateSspSlotAudit(SspAdvertSlotAudit slotAudit) {
		return this.channelAdvertSlotAuditMapper.updateSspSlotAudit(slotAudit);
	}

	public Integer queryMaxSlotId(Integer sspPartnerId, Integer appId) {
		return this.channelAdvertSlotAuditMapper.queryMaxSlotId(sspPartnerId, appId);
	}



}
