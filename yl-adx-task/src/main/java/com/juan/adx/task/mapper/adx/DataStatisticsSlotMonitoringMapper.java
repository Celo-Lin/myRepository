package com.juan.adx.task.mapper.adx;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import com.juan.adx.model.entity.manage.DataStatisticsSlotMonitoring;

@Mapper
public interface DataStatisticsSlotMonitoringMapper {
	
	
	@Insert(" 	INSERT IGNORE INTO adx_data_statistics_slot_monitoring ("
			+ "		fad_slot_id,"
			+ "		frequest_count,"
			+ "		ffill_count,"
			+ "		fdisplay_count,"
			+ "		fclick_count,"
			+ "		flast_request_time,"
			+ "		flast_fill_time,"
			+ "		flast_display_time,"
			+ "		flast_click_time,"
			+ "		fdate"
			+ "	) VALUES ("
			+ "		#{slotMonitoring.adSlotId},"
			+ "		#{slotMonitoring.requestCount},"
			+ "		#{slotMonitoring.fillCount},"
			+ "		#{slotMonitoring.displayCount},"
			+ "		#{slotMonitoring.clickCount},"
			+ "		#{slotMonitoring.lastRequestTime},"
			+ "		#{slotMonitoring.lastFillTime},"
			+ "		#{slotMonitoring.lastDisplayTime},"
			+ "		#{slotMonitoring.lastClickTime},"
			+ "		#{slotMonitoring.date}"
			+ ")")
	int saveSlotMonitoring(@Param("slotMonitoring") DataStatisticsSlotMonitoring slotMonitoring);
	
	@Update(" 	UPDATE adx_data_statistics_slot_monitoring "
			+ "	SET frequest_count = #{slotMonitoring.requestCount},"
			+ "		ffill_count = #{slotMonitoring.fillCount},"
			+ "		fdisplay_count = #{slotMonitoring.displayCount},"
			+ "		fclick_count = #{slotMonitoring.clickCount},"
			+ "		flast_request_time = IF(#{slotMonitoring.lastRequestTime} > 0, #{slotMonitoring.lastRequestTime}, flast_request_time),"
			+ "		flast_fill_time = IF(#{slotMonitoring.lastFillTime} > 0, #{slotMonitoring.lastFillTime}, flast_fill_time),"
			+ "		flast_display_time = IF(#{slotMonitoring.lastDisplayTime} > 0, #{slotMonitoring.lastDisplayTime}, flast_display_time),"
			+ "		flast_click_time = IF(#{slotMonitoring.lastClickTime} > 0, #{slotMonitoring.lastClickTime}, flast_click_time)"
			+ "	WHERE fdate = #{slotMonitoring.date} AND fad_slot_id = #{slotMonitoring.adSlotId} ")
	int updateSlotMonitoring(@Param("slotMonitoring") DataStatisticsSlotMonitoring slotMonitoring);
	

}
