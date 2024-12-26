package com.juan.adx.task.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.juan.adx.task.mapper.adx.DspPartnerMapper;
import com.juan.adx.model.entity.manage.DspPartner;

@Service
public class DspPartnerService {

	@Resource
	private DspPartnerMapper dspPartnerMapper;

	public DspPartner getDspPartner(Integer dspPartnerId) {
		return this.dspPartnerMapper.queryDspPartner(dspPartnerId);
	}
}
