package com.juan.adx.manage.dao.adx;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.juan.adx.manage.mapper.adx.DataStatisticsIndexMapper;
import com.juan.adx.model.entity.manage.DataStatisticsTrendIndex;
import com.juan.adx.model.form.manage.IndexTrendDataForm;

@Repository
public class DataStatisticsIndexDao {

	@Resource
	private DataStatisticsIndexMapper dataStatisticsIndexMapper;

	public List<DataStatisticsTrendIndex> queryIndexTrendData(IndexTrendDataForm form) {
		return this.dataStatisticsIndexMapper.queryIndexTrendData(form);
	}
}
