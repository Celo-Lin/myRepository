package com.juan.adx.manage.service.adx;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.juan.adx.manage.dao.adx.DictDao;
import com.juan.adx.model.entity.manage.AppStore;
import com.juan.adx.model.entity.manage.Industry;

@Service
public class DictService {

	@Resource
	private DictDao dictDao;

	public List<AppStore> listAppstore() {
		return this.dictDao.allAppStore();
	}
	
	public List<Industry> listIndustry() {
		return this.dictDao.allIndustry();
	}
	
}
