package com.juan.adx.channel.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.juan.adx.channel.mapper.permission.ChannelUserMapper;
import com.juan.adx.model.entity.sspmanage.ChannelUser;


@Repository
public class ChannelUserDao{

	@Autowired
	private ChannelUserMapper channelUserMapper;

	
	public ChannelUser getUserWithPasswordByAccountId(String accountId) {
		return this.channelUserMapper.queryUserWithPasswordByAccountId(accountId);
	}

	public int updatePassword(ChannelUser user) {
		return this.channelUserMapper.updatePassword(user);
	}

	public ChannelUser queryChannelUserWithPassword(Integer sspPartnerId, Integer userId) {
		return this.channelUserMapper.queryChannelUserWithPassword(sspPartnerId, userId);
	}
	
}
