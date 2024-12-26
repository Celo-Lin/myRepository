package com.juan.adx.manage.service.permission;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.juan.adx.manage.dao.permission.ChannelUserDao;
import com.juan.adx.manage.service.adx.SspPartnerService;
import com.juan.adx.common.alg.MD5Util;
import com.juan.adx.common.alg.bcrypt.BCrypt;
import com.juan.adx.common.model.PageData;
import com.juan.adx.common.utils.LocalDateUtils;
import com.juan.adx.common.validator.ParamAssert;
import com.juan.adx.model.constants.ManageCommonConstants;
import com.juan.adx.model.dto.sspmanage.ChannelUserDto;
import com.juan.adx.model.entity.manage.SspPartner;
import com.juan.adx.model.entity.sspmanage.ChannelUser;
import com.juan.adx.model.enums.Status;
import com.juan.adx.model.form.sspmanage.ChannelUserForm;

@Service
public class ChannelUserService {

	@Resource
	private ChannelUserDao channelUserDao;
	
	@Resource
	private SspPartnerService sspPartnerService;
	
	public boolean existChannelUserByAccountId(String accountId) {
		int count = this.channelUserDao.countChannelUserByAccountId(accountId);
		return count > 0;
	}

	public PageData listChannelUser(ChannelUserForm form) {
		PageHelper.startPage(form.getPageNo(), form.getPageSize());
        List<ChannelUserDto> dataList = this.channelUserDao.queryChannelUserList(form);
        for (ChannelUserDto channelUserDto : dataList) {
        	SspPartner sspPartner = this.sspPartnerService.getSspPartner(channelUserDto.getSspPartnerId());
        	if(sspPartner == null) {
        		continue;
        	}
        	channelUserDto.setSspPartnerName(sspPartner.getName());
		}
        PageInfo<ChannelUserDto> pageInfo = new PageInfo<ChannelUserDto>(dataList);
		return new PageData().addPageInfo(pageInfo, dataList);	
	}

	public ChannelUserDto getChannelUser(Integer userId) {
		ChannelUserDto channelUserDto = this.channelUserDao.queryChannelUser(userId);
		if(channelUserDto == null) {
			return channelUserDto;
		}
		SspPartner sspPartner = this.sspPartnerService.getSspPartner(channelUserDto.getSspPartnerId());
    	if(sspPartner != null) {
    		channelUserDto.setSspPartnerName(sspPartner.getName());
    	}
		return channelUserDto;
	}

	public void updateChannelUser(ChannelUser channelUser) {
		this.channelUserDao.updateChannelUser(channelUser);
	}

	public void updatePassword(ChannelUser channelUser) {
		String password = channelUser.getPassword();
		String salt = BCrypt.gensalt(ManageCommonConstants.Password.rounds);
		password = BCrypt.hashpw(MD5Util.getMD5String(password), salt);
		channelUser.setPassword(password);
		this.channelUserDao.updatePassword(channelUser);
	}

	public void addChannelUser(ChannelUser channelUser) {
		long nowSeconds = LocalDateUtils.getNowSeconds();
		String password = channelUser.getPassword();
		String salt = BCrypt.gensalt(ManageCommonConstants.Password.rounds);
		password = BCrypt.hashpw(MD5Util.getMD5String(password), salt);
		channelUser.setPassword(password);
		channelUser.setCtime(nowSeconds);
		channelUser.setUtime(nowSeconds);
		channelUser.setStatus(Status.VALID.getStatus());
		this.channelUserDao.saveChannelUser(channelUser);
	}

	public void updateStatus(Integer userId, Integer status) {
		Status statusEnum = Status.get(status);
		ParamAssert.isTrue(statusEnum == null, "预算状态无效");
		this.channelUserDao.updateStatus(userId, status);
	}
}
