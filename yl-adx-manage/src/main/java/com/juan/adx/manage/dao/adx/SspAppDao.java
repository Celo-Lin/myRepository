package com.juan.adx.manage.dao.adx;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.juan.adx.manage.mapper.adx.SspAppMapper;
import com.juan.adx.model.dto.manage.SspAppDto;
import com.juan.adx.model.dto.manage.SspAppOptionDto;
import com.juan.adx.model.entity.manage.SspApp;
import com.juan.adx.model.form.manage.SspAppForm;

@Repository
public class SspAppDao {

	@Resource
	private SspAppMapper sspAppMapper;

	public List<SspAppDto> querySspApp(SspAppForm form) {
		return this.sspAppMapper.querySspAppList(form);
	}

	public int saveSspApp(SspApp app) {
		return this.sspAppMapper.saveSspApp(app);
	}

	public SspAppDto querySspApp(Integer id) {
		return this.sspAppMapper.querySspApp(id);
	}

	public int updateSspApp(SspApp sspApp) {
		return this.sspAppMapper.updateSspApp(sspApp);
	}
	
	public int syncChannelUpdateToSspApp(SspApp sspApp) {
		return this.sspAppMapper.syncChannelUpdateToSspApp(sspApp);
	}

	public int updateSspAppStatus(Integer id, Integer status) {
		return this.sspAppMapper.updateSspAppStatus(id, status);
	}

	public int deleteSspApp(Integer id) {
		return this.sspAppMapper.deleteSspApp(id);
	}

	public List<SspAppOptionDto> querySspAppOption(String name) {
		return this.sspAppMapper.querySspAppOption(name);
	}

	public List<SspAppOptionDto> querySspAppSimpleBySspPartnerId(Integer sspPartnerId) {
		return this.sspAppMapper.querySspAppSimpleBySspPartnerId(sspPartnerId);
	}

}
