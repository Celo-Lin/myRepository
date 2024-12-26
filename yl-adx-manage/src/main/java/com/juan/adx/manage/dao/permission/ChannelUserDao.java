package com.juan.adx.manage.dao.permission;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.juan.adx.manage.mapper.permission.ChannelUserMapper;
import com.juan.adx.model.dto.sspmanage.ChannelUserDto;
import com.juan.adx.model.entity.sspmanage.ChannelUser;
import com.juan.adx.model.form.sspmanage.ChannelUserForm;

@Repository
public class ChannelUserDao {
	
	@Resource
	private ChannelUserMapper channelUserMapper;

	public List<ChannelUserDto> queryChannelUserList(ChannelUserForm form) {
		return this.channelUserMapper.queryChannelUserList(form);
	}

	public ChannelUserDto queryChannelUser(Integer userId) {
		return this.channelUserMapper.queryChannelUser(userId);
	}

	public int updateChannelUser(ChannelUser channelUser) {
		return this.channelUserMapper.updateChannelUser(channelUser);
	}

	public int updatePassword(ChannelUser channelUser) {
		return this.channelUserMapper.updatePassword(channelUser);
	}

	public int saveChannelUser(ChannelUser channelUser) {
		return this.channelUserMapper.saveChannelUser(channelUser);
	}

	public int countChannelUserByAccountId(String accountId) {
		return this.channelUserMapper.countChannelUserByAccountId(accountId);
	}

	public int updateStatus(Integer userId, Integer status) {
		return this.channelUserMapper.updateStatus(userId, status);
	}

}
