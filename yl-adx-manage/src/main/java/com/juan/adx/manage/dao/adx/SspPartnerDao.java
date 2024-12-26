package com.juan.adx.manage.dao.adx;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.juan.adx.manage.mapper.adx.SspPartnerMapper;
import com.juan.adx.model.dto.manage.SspPartnerOptionDto;
import com.juan.adx.model.entity.manage.SspPartner;
import com.juan.adx.model.form.manage.SspPartnerForm;

@Repository
public class SspPartnerDao {

	@Resource
	private SspPartnerMapper sspPartnerMapper;

	public List<SspPartner> querySspPartners(SspPartnerForm form) {
		return this.sspPartnerMapper.querySspPartnerList(form);
	}

	public int saveSspPartner(SspPartner sspPartner) {
		return this.sspPartnerMapper.saveSspPartner(sspPartner);
	}

	public SspPartner querySspPartner(Integer id) {
		return this.sspPartnerMapper.querySspPartner(id);
	}

	public int updateSspPartner(SspPartner sspPartner) {
		return this.sspPartnerMapper.updateSspPartner(sspPartner);
	}

	public int deleteSspPartner(Integer id) {
		return this.sspPartnerMapper.deleteSspPartner(id);
	}

	public List<SspPartnerOptionDto> querySspPartnerOption(String name) {
		return this.sspPartnerMapper.querySspPartnerOption(name);
	}
}
