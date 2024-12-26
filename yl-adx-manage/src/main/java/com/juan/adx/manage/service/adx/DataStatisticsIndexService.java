package com.juan.adx.manage.service.adx;

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
import com.juan.adx.manage.dao.adx.DataStatisticsIndexDao;
import com.juan.adx.common.cache.RedisKeyUtil;
import com.juan.adx.common.cache.RedisTemplate;
import com.juan.adx.common.utils.LocalDateUtils;
import com.juan.adx.model.dto.manage.DataStatisticsIndexIncomeDto;
import com.juan.adx.model.dto.manage.DataStatisticsIndexTrendDto;
import com.juan.adx.model.dto.manage.DataStatisticsIndexTrendItemDto;
import com.juan.adx.model.entity.manage.DataStatisticsTrendIndex;
import com.juan.adx.model.enums.IndexTrendType;
import com.juan.adx.model.form.manage.IndexTrendDataForm;

@Service
public class DataStatisticsIndexService {

	@Resource
	private DataStatisticsIndexDao dataStatisticsIndexDao;
	
	@Resource
	private RedisTemplate redisTemplate;

	
	
	public DataStatisticsIndexIncomeDto getIncomeData() {
		String todayStr = LocalDateUtils.formatLocalDateToString(LocalDate.now(), LocalDateUtils.DATE_PLAIN_FORMATTER);
		String key = RedisKeyUtil.getIndexIncomeKey(todayStr);
		String value = this.redisTemplate.STRINGS.get(key);
		DataStatisticsIndexIncomeDto incomeDto = null;
		if(StringUtils.isNotBlank(value)) {
			incomeDto = JSON.parseObject(value, DataStatisticsIndexIncomeDto.class);
		}
		return incomeDto;
	}

	public List<DataStatisticsIndexTrendDto> getIndexTrendData(IndexTrendDataForm form) {
		List<DataStatisticsTrendIndex> dataStatisticsTrendList = this.dataStatisticsIndexDao.queryIndexTrendData(form);
		Map<Long, List<DataStatisticsTrendIndex>> dataStatisticsTrendMap = dataStatisticsTrendList.stream().collect(Collectors.groupingBy(DataStatisticsTrendIndex::getDate));
		List<DataStatisticsIndexTrendDto> resultList = new ArrayList<DataStatisticsIndexTrendDto>();
		IndexTrendType trendType = IndexTrendType.get(form.getTrendType());
		
		for (Entry<Long, List<DataStatisticsTrendIndex>> entry : dataStatisticsTrendMap.entrySet()) {
			DataStatisticsIndexTrendDto resultObject = new DataStatisticsIndexTrendDto();
			resultObject.setDate(entry.getKey());
			List<DataStatisticsTrendIndex> value = entry.getValue();
			List<DataStatisticsIndexTrendItemDto> itemList = new ArrayList<DataStatisticsIndexTrendItemDto>();
			for (DataStatisticsTrendIndex subList : value) {
				DataStatisticsIndexTrendItemDto item = new DataStatisticsIndexTrendItemDto();
				item.setAdSlotType(subList.getAdSlotType());
				int num = 0;
				switch (trendType) {
				case CLICK:
					num = subList.getClickCount();
					break;
				case DISPLAY:
					num = subList.getDisplayCount();
					break;
				case FILL:
					num = subList.getFillCount();
					break;
				case REQUEST:
					num = subList.getRequestCount();
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
        List<DataStatisticsIndexTrendDto> sortedList = resultList.stream()
                .sorted(Comparator.comparing(DataStatisticsIndexTrendDto::getDate))
                .collect(Collectors.toList());
		return sortedList;
	}

}
