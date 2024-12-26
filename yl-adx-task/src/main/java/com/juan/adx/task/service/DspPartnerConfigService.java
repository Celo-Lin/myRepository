package com.juan.adx.task.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.juan.adx.task.mapper.adx.DspPartnerConfigMapper;
import com.juan.adx.model.entity.manage.DspPartnerConfig;

@Service
public class DspPartnerConfigService {

	@Resource
	private DspPartnerConfigMapper dspPartnerConfigMapper;

	public DspPartnerConfig getDspPartnerConfig(Integer dspPartnerId) {
		return this.dspPartnerConfigMapper.queryDspPartnerConfig(dspPartnerId);
	}
	
}
