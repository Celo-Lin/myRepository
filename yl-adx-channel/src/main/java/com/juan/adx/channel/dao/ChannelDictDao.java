package com.juan.adx.channel.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.juan.adx.channel.mapper.adx.ChannelDictMapper;
import com.juan.adx.model.entity.manage.AppStore;
import com.juan.adx.model.entity.manage.Industry;

@Repository
public class ChannelDictDao {

	@Resource
	private ChannelDictMapper channelDictMapper;

	public List<AppStore> allAppStore() {
		return this.channelDictMapper.allAppStore();
	}
	
	public List<Industry> allIndustry() {
		return this.channelDictMapper.allIndustry();
	}
}
