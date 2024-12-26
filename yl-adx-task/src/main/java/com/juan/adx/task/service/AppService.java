package com.juan.adx.task.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.juan.adx.task.mapper.adx.AppMapper;
import com.juan.adx.model.entity.manage.SspApp;

@Service
public class AppService {

	@Resource
	private AppMapper appMapper;

	public SspApp getAppById(Integer id) {
		return this.appMapper.queryAppById(id);
	}
}
