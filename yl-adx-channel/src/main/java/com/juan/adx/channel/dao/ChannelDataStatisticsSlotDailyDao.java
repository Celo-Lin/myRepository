package com.juan.adx.channel.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.juan.adx.channel.mapper.adx.ChannelDataStatisticsSlotDailyMapper;
import com.juan.adx.model.dto.sspmanage.ChannelDataStatisticsSlotDailyDto;
import com.juan.adx.model.form.sspmanage.ChannelDataStatisticsSlotDailyForm;

@Repository
public class ChannelDataStatisticsSlotDailyDao {

	@Resource
	private ChannelDataStatisticsSlotDailyMapper channelDataStatisticsSlotDailyMapper;

	public List<ChannelDataStatisticsSlotDailyDto> querySspDataStatementSlotDailyList(ChannelDataStatisticsSlotDailyForm form) {
		return this.channelDataStatisticsSlotDailyMapper.querySspDataStatementSlotDailyList(form);
	}

}
