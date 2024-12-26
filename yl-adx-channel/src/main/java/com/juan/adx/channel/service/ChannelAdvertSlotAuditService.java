package com.juan.adx.channel.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.juan.adx.channel.dao.ChannelAdvertSlotAuditDao;
import com.juan.adx.channel.dao.ChannelAppAuditDao;
import com.juan.adx.common.model.PageData;
import com.juan.adx.common.utils.LocalDateUtils;
import com.juan.adx.model.constants.ManageCommonConstants;
import com.juan.adx.model.dto.sspmanage.ChannelAdvertSlotAuditDto;
import com.juan.adx.model.dto.sspmanage.ChannelAppAuditDto;
import com.juan.adx.model.entity.manage.SspAdvertSlotAudit;
import com.juan.adx.model.enums.SspAuditStatus;
import com.juan.adx.model.form.sspmanage.ChannelAdvertSlotForm;

@Service
public class ChannelAdvertSlotAuditService {

	@Resource
	private ChannelAdvertSlotAuditDao channelAdvertSlotAuditDao;
	
	@Resource
	private ChannelAppAuditDao channelAppAuditDao;

	public PageData listSspAdvertSlotAudit(ChannelAdvertSlotForm form) {
		PageHelper.startPage(form.getPageNo(), form.getPageSize());
        List<ChannelAdvertSlotAuditDto> dataList = this.channelAdvertSlotAuditDao.querySspAdvertSlotAuditList(form);
        PageInfo<ChannelAdvertSlotAuditDto> pageInfo = new PageInfo<ChannelAdvertSlotAuditDto>(dataList);
		return new PageData().addPageInfo(pageInfo, dataList);	
	}

	public ChannelAdvertSlotAuditDto getSspAdvertSlotAudit(Integer sspPartnerId, Integer id) {
		return this.channelAdvertSlotAuditDao.querySspAdvertSlotAudit(sspPartnerId, id);
	}
	
	private int generateSlotId(Integer sspPartnerId, Integer appId) {
		Integer maxSlotId = this.channelAdvertSlotAuditDao.queryMaxSlotId(sspPartnerId, appId);
		if(maxSlotId != null && maxSlotId.intValue() > 0) {
			return ++maxSlotId;
		}
		ChannelAppAuditDto appAuditDto = this.channelAppAuditDao.querySspAppAudit(sspPartnerId, appId);
		maxSlotId = appAuditDto.getId() * ManageCommonConstants.ID_STEP_SIZE + 1;
		return maxSlotId;
	}

	public boolean saveSspAdvertSlotAudit(SspAdvertSlotAudit advertSlotAudit) {
		
		long nowSeconds = LocalDateUtils.getNowSeconds();
		Integer id = this.generateSlotId(advertSlotAudit.getSspPartnerId(), advertSlotAudit.getAppId());
		advertSlotAudit.setId(id);
		advertSlotAudit.setAuditStatus(SspAuditStatus.INSERT_UNAUDITED.getStatus());
		advertSlotAudit.setAuditComments(StringUtils.EMPTY);
		advertSlotAudit.setCtime(nowSeconds);
		advertSlotAudit.setUtime(nowSeconds);
		int ret = this.channelAdvertSlotAuditDao.saveSspAdvertSlotAudit(advertSlotAudit);
		return ret > 0;
	}

	public boolean updateSspAdvertSlotAudit(SspAdvertSlotAudit advertSlotAudit) {
		long nowSeconds = LocalDateUtils.getNowSeconds();
		advertSlotAudit.setAuditStatus(SspAuditStatus.UPDATE_UNAUDITED.getStatus());
		advertSlotAudit.setAuditComments(StringUtils.EMPTY);
		advertSlotAudit.setUtime(nowSeconds);
		this.channelAdvertSlotAuditDao.updateSspSlotAudit(advertSlotAudit);
		return true;
	}

}
