package com.juan.adx.task.mapper.adx;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.juan.adx.model.entity.manage.DataStatisticsTrendIndex;
import com.juan.adx.model.entity.sspmanage.ChannelDataStatisticsTrendIndex;

@Mapper
public interface DataStatisticsIndexTrendMapper {

	@Insert(" INSERT INTO adx_data_statistics_trend_index ("
			+ "	frequest_count,"
			+ "	ffill_count,"
			+ "	fdisplay_count,"
			+ "	fclick_count,"
			+ "	fad_slot_type,"
			+ "	fdate"
			+ ") VALUES ("
			+ "	#{trendIndex.requestCount},"
			+ "	#{trendIndex.fillCount},"
			+ "	#{trendIndex.displayCount},"
			+ "	#{trendIndex.clickCount},"
			+ "	#{trendIndex.adSlotType},"
			+ "	#{trendIndex.date}"
			+ ") ON DUPLICATE KEY UPDATE "
			+ "		frequest_count = VALUES(frequest_count), "
			+ "		ffill_count = VALUES(ffill_count), "
			+ "		fdisplay_count = VALUES(fdisplay_count), "
			+ "		fclick_count = VALUES(fclick_count) ")
	int saveTrendIndex(@Param("trendIndex") DataStatisticsTrendIndex trendIndex);

	
	@Insert(" INSERT INTO adx_data_statistics_trend_index_ssp ("
			+ "	fssp_partner_id,"
			+ "	ffill_count,"
			+ "	fdisplay_count,"
			+ "	fad_slot_type,"
			+ "	fdate"
			+ ") VALUES ("
			+ "	#{trendIndex.sspPartnerId},"
			+ "	#{trendIndex.fillCount},"
			+ "	#{trendIndex.displayCount},"
			+ "	#{trendIndex.adSlotType},"
			+ "	#{trendIndex.date}"
			+ ") ON DUPLICATE KEY UPDATE ffill_count = VALUES(ffill_count),"
			+ "		fdisplay_count = VALUES(fdisplay_count) ")
	int saveTrendIndexSsp(@Param("trendIndex") ChannelDataStatisticsTrendIndex trendIndex);

}
