package com.juan.adx.task.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.juan.adx.task.mapper.adx.DataStatisticsIndexTrendMapper;
import com.juan.adx.model.entity.manage.DataStatisticsTrendIndex;
import com.juan.adx.model.entity.sspmanage.ChannelDataStatisticsTrendIndex;

@Service
public class DataStatisticsIndexTrendService {

	@Resource
	private DataStatisticsIndexTrendMapper indexTrendMapper;

	public boolean saveTrendIndex(DataStatisticsTrendIndex trendIndex) {
		int ret = this.indexTrendMapper.saveTrendIndex(trendIndex);
		return ret > 0;
	}

	public boolean saveTrendIndexSsp(ChannelDataStatisticsTrendIndex trendIndex) {
		int ret = this.indexTrendMapper.saveTrendIndexSsp(trendIndex);
		return ret > 0;
	}

}
