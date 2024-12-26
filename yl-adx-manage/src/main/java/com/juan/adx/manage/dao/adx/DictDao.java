package com.juan.adx.manage.dao.adx;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.juan.adx.manage.mapper.adx.DictMapper;
import com.juan.adx.model.entity.manage.AppStore;
import com.juan.adx.model.entity.manage.Industry;

@Repository
public class DictDao {

	@Resource
	private DictMapper dictMapper;

	public List<AppStore> allAppStore() {
		return this.dictMapper.allAppStore();
	}
	
	public List<Industry> allIndustry() {
		return this.dictMapper.allIndustry();
	}
}
