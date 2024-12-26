package com.juan.adx.manage.dao.adx;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.juan.adx.manage.mapper.adx.DspPartnerMapper;
import com.juan.adx.model.dto.manage.DspPartnerOptionDto;
import com.juan.adx.model.entity.manage.DspPartner;
import com.juan.adx.model.form.manage.DspPartnerForm;

@Repository
public class DspPartnerDao {
	
	@Resource
	private DspPartnerMapper dspPartnerMapper;

	public List<DspPartner> queryDspPartnerList(DspPartnerForm form) {
		return this.dspPartnerMapper.queryDspPartnerList(form);
	}

	public List<DspPartnerOptionDto> queryDspPartnerOption(String name) {
		return this.dspPartnerMapper.queryDspPartnerOption(name);
	}

	public int saveDspPartner(DspPartner dspPartner) {
		return this.dspPartnerMapper.saveDspPartner(dspPartner);
	}

	public DspPartner queryDspPartner(Integer id) {
		return this.dspPartnerMapper.queryDspPartner(id);
	}

	public int updateDspPartner(DspPartner dspPartner) {
		return this.dspPartnerMapper.updateDspPartner(dspPartner);
	}

	public int deleteDspPartner(Integer id) {
		return this.dspPartnerMapper.deleteDspPartner(id);
	}


}
