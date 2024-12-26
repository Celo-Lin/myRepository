package com.juan.adx.channel.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.juan.adx.channel.mapper.adx.ChannelDataStatisticsIndexMapper;
import com.juan.adx.model.entity.sspmanage.ChannelDataStatisticsTrendIndex;
import com.juan.adx.model.form.sspmanage.ChannelIndexTrendDataForm;

@Repository
public class ChannelDataStatisticsIndexDao {

	@Resource
	private ChannelDataStatisticsIndexMapper channelDataStatisticsIndexMapper;

	public List<ChannelDataStatisticsTrendIndex> queryIndexTrendData(ChannelIndexTrendDataForm form) {
		return this.channelDataStatisticsIndexMapper.queryIndexTrendData(form);
	}
}
