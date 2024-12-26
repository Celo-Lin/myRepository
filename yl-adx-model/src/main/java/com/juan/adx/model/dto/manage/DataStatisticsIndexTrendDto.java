package com.juan.adx.model.dto.manage;

import java.util.List;

import lombok.Data;

@Data
public class DataStatisticsIndexTrendDto {

	private Long date;

	private List<DataStatisticsIndexTrendItemDto> items;
	
}
