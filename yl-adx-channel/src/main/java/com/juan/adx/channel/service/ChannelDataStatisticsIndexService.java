package com.juan.adx.channel.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson2.JSON;
import com.juan.adx.channel.dao.ChannelDataStatisticsIndexDao;
import com.juan.adx.common.cache.RedisKeyUtil;
import com.juan.adx.common.cache.RedisTemplate;
import com.juan.adx.common.utils.LocalDateUtils;
import com.juan.adx.model.dto.sspmanage.ChannelDataStatisticsIndexIncomeDto;
import com.juan.adx.model.dto.sspmanage.ChannelDataStatisticsIndexTrendDto;
import com.juan.adx.model.dto.sspmanage.ChannelDataStatisticsIndexTrendItemDto;
import com.juan.adx.model.entity.sspmanage.ChannelDataStatisticsTrendIndex;
import com.juan.adx.model.enums.ChannelIndexTrendType;
import com.juan.adx.model.form.sspmanage.ChannelIndexTrendDataForm;

@Service
public class ChannelDataStatisticsIndexService {

	@Resource
	private ChannelDataStatisticsIndexDao channelDataStatisticsIndexDao;
	
	@Resource
	private RedisTemplate redisTemplate;
	

	public ChannelDataStatisticsIndexIncomeDto getIncomeData(Integer sspPartnerId) {
		String todayStr = LocalDateUtils.formatLocalDateToString(LocalDate.now(), LocalDateUtils.DATE_PLAIN_FORMATTER);
		String key = RedisKeyUtil.getIndexIncomeSspKey(todayStr, sspPartnerId);
		String value = this.redisTemplate.STRINGS.get(key);
		ChannelDataStatisticsIndexIncomeDto incomeDto = null;
		if(StringUtils.isNotBlank(value)) {
			incomeDto = JSON.parseObject(value, ChannelDataStatisticsIndexIncomeDto.class);
		}
		return incomeDto;
	}

	public List<ChannelDataStatisticsIndexTrendDto> getIndexTrendData(ChannelIndexTrendDataForm form) {
		List<ChannelDataStatisticsTrendIndex> dataStatisticsTrendList = this.channelDataStatisticsIndexDao.queryIndexTrendData(form);
		Map<Long, List<ChannelDataStatisticsTrendIndex>> dataStatisticsTrendMap = dataStatisticsTrendList.stream().collect(Collectors.groupingBy(ChannelDataStatisticsTrendIndex::getDate));
		List<ChannelDataStatisticsIndexTrendDto> resultList = new ArrayList<ChannelDataStatisticsIndexTrendDto>();
		ChannelIndexTrendType trendType = ChannelIndexTrendType.get(form.getTrendType());
		
		for (Entry<Long, List<ChannelDataStatisticsTrendIndex>> entry : dataStatisticsTrendMap.entrySet()) {
			ChannelDataStatisticsIndexTrendDto resultObject = new ChannelDataStatisticsIndexTrendDto();
			resultObject.setDate(entry.getKey());
			List<ChannelDataStatisticsTrendIndex> value = entry.getValue();
			List<ChannelDataStatisticsIndexTrendItemDto> itemList = new ArrayList<ChannelDataStatisticsIndexTrendItemDto>();
			for (ChannelDataStatisticsTrendIndex subList : value) {
				ChannelDataStatisticsIndexTrendItemDto item = new ChannelDataStatisticsIndexTrendItemDto();
				item.setAdSlotType(subList.getAdSlotType());
				int num = 0;
				switch (trendType) {
				case DISPLAY:
					num = subList.getDisplayCount();
					break;
				case FILL:
					num = subList.getFillCount();
					break;
				default:
					break;
				}
				item.setNum(num);
				itemList.add(item);
			}
			resultObject.setItems(itemList);
			resultList.add(resultObject);
		}
        List<ChannelDataStatisticsIndexTrendDto> sortedList = resultList.stream()
                .sorted(Comparator.comparing(ChannelDataStatisticsIndexTrendDto::getDate))
                .collect(Collectors.toList());
		return sortedList;
	}

}
