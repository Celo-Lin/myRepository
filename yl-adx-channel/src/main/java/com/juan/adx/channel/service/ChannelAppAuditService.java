package com.juan.adx.channel.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.juan.adx.channel.dao.ChannelAppAuditDao;
import com.juan.adx.common.model.PageData;
import com.juan.adx.common.utils.LocalDateUtils;
import com.juan.adx.model.dto.sspmanage.ChannelAppAuditDto;
import com.juan.adx.model.dto.sspmanage.ChannelAppOptionDto;
import com.juan.adx.model.entity.manage.SspAppAudit;
import com.juan.adx.model.enums.SspAuditStatus;
import com.juan.adx.model.form.sspmanage.ChannelAppForm;


@Service
public class ChannelAppAuditService {

	@Resource
	private ChannelAppAuditDao channelAppAuditDao;
	
	
	public PageData listSspAppAudit(ChannelAppForm form) {
		PageHelper.startPage(form.getPageNo(), form.getPageSize());
        List<ChannelAppAuditDto> dataList = this.channelAppAuditDao.querySspAppAuditList(form);
        PageInfo<ChannelAppAuditDto> pageInfo = new PageInfo<ChannelAppAuditDto>(dataList);
		return new PageData().addPageInfo(pageInfo, dataList);	
	}


	public boolean saveSspAppAudit(SspAppAudit appAudit) {
		long nowSeconds = LocalDateUtils.getNowSeconds();
		appAudit.setAuditStatus(SspAuditStatus.INSERT_UNAUDITED.getStatus());
		appAudit.setAuditComments(StringUtils.EMPTY);
		appAudit.setCtime(nowSeconds);
		appAudit.setUtime(nowSeconds);
		int ret = this.channelAppAuditDao.saveSspAppAudit(appAudit);
		return ret > 0;
	}


	public ChannelAppAuditDto getSspAppAudit(Integer sspPartnerId, Integer id) {
		return this.channelAppAuditDao.querySspAppAudit(sspPartnerId, id);
	}


	public boolean updateSspAppAudit(SspAppAudit appAudit) {
		long nowSeconds = LocalDateUtils.getNowSeconds();
		appAudit.setUtime(nowSeconds);
		appAudit.setAuditStatus(SspAuditStatus.UPDATE_UNAUDITED.getStatus());
		appAudit.setAuditComments(StringUtils.EMPTY);
		this.channelAppAuditDao.updateSspAppAudit(appAudit);
		return true;
	}


	public List<ChannelAppOptionDto> listSspAppAuditOption(Integer sspPartnerId, String name) {
		if(StringUtils.isBlank(name)) {
			return null;
		}
		return this.channelAppAuditDao.querySspAppAuditOption(sspPartnerId, name);
	}

	
}
