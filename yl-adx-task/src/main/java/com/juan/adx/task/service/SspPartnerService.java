package com.juan.adx.task.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.juan.adx.task.mapper.adx.SspPartnerMapper;
import com.juan.adx.model.entity.manage.SspPartner;

@Service
public class SspPartnerService {

	@Resource
	private SspPartnerMapper sspPartnerMapper;

	public List<SspPartner> getSspPartnerAll() {
		return this.sspPartnerMapper.querySspPartnerAll();
	}
}
