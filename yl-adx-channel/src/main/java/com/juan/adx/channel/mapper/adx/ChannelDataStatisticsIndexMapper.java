package com.juan.adx.channel.mapper.adx;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.juan.adx.model.entity.sspmanage.ChannelDataStatisticsTrendIndex;
import com.juan.adx.model.form.sspmanage.ChannelIndexTrendDataForm;

@Mapper
public interface ChannelDataStatisticsIndexMapper {

	String SQL_COLUMN = "	fid AS id,"
			+ "				fssp_partner_id AS sspPartnerId,"
			+ "				ffill_count AS fillCount,"
			+ "				fdisplay_count AS displayCount,"
			+ "				fad_slot_type AS adSlotType,"
			+ "				fdate AS date";
	
	
	@Select("	SELECT  " + SQL_COLUMN
			+ "	FROM adx_data_statistics_trend_index_ssp "
			+ "	WHERE fssp_partner_id = #{form.sspPartnerId} AND fdate BETWEEN #{form.startTime} AND #{form.endTime}")
	List<ChannelDataStatisticsTrendIndex> queryIndexTrendData(@Param("form") ChannelIndexTrendDataForm form);

}
