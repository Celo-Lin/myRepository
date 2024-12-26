package com.juan.adx.manage.mapper.adx;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.juan.adx.model.entity.manage.DataStatisticsTrendIndex;
import com.juan.adx.model.form.manage.IndexTrendDataForm;

@Mapper
public interface DataStatisticsIndexMapper {

	String SQL_COLUMN = "	fid AS id,"
			+ "				frequest_count AS requestCount,"
			+ "				ffill_count AS fillCount,"
			+ "				fdisplay_count AS displayCount,"
			+ "				fclick_count AS clickCount,"
			+ "				fad_slot_type AS adSlotType,"
			+ "				fdate AS date";
	
	
	@Select("	SELECT  " + SQL_COLUMN
			+ "	FROM adx_data_statistics_trend_index "
			+ "	WHERE fdate BETWEEN #{form.startTime} AND #{form.endTime}")
	List<DataStatisticsTrendIndex> queryIndexTrendData(@Param("form") IndexTrendDataForm form);

}
