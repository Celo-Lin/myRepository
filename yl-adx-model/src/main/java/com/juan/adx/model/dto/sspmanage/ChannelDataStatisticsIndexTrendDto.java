package com.juan.adx.model.dto.sspmanage;

import java.util.List;

import lombok.Data;

@Data
public class ChannelDataStatisticsIndexTrendDto {

	private Long date;

	private List<ChannelDataStatisticsIndexTrendItemDto> items;
	
}
