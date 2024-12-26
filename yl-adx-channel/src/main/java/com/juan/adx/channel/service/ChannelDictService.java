package com.juan.adx.channel.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.juan.adx.channel.dao.ChannelDictDao;
import com.juan.adx.model.entity.manage.AppStore;
import com.juan.adx.model.entity.manage.Industry;

@Service
public class ChannelDictService {

	@Resource
	private ChannelDictDao channelDictDao;

	public List<AppStore> listAppstore() {
		return this.channelDictDao.allAppStore();
	}
	
	public List<Industry> listIndustry() {
		return this.channelDictDao.allIndustry();
	}
	
}
