package com.juan.adx.task.mapper.adx;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.juan.adx.model.entity.manage.DataStatisticsSlotDaily;

@Mapper
public interface DataStatisticsSlotDailyMapper {
	
	@Select("	SELECT SUM(fdsp_estimate_income)"
			+ "	FROM adx_data_statistics_slot_daily "
			+ "	WHERE fdate BETWEEN #{startTime} AND #{endTime}")
	Long sumDspEstimateIncomeByDate(@Param("startTime") long startTime, @Param("endTime") long endTime);

	@Insert(" 	INSERT IGNORE INTO adx_data_statistics_slot_daily ("
			+ "		fssp_partner_id,"
			+ "		fdsp_partner_id,"
			+ "		fapp_id,"
			+ "		fad_slot_id,"
			+ "		fbudget_id,"
			+ "		fmax_requests,"
			+ "		frequest_count,"
			+ "		ffill_count,"
			+ "		fdisplay_count,"
			+ "		fclick_count,"
			+ "		fdownload_count,"
			+ "		finstall_count,"
			+ "		fdeeplink_count,"
			+ "		fssp_estimate_income,"
			+ "		fdsp_estimate_income,"
			+ "		fdate"
			+ "	) VALUES ("
			+ "		#{slotDaily.sspPartnerId},"
			+ "		#{slotDaily.dspPartnerId},"
			+ "		#{slotDaily.appId},"
			+ "		#{slotDaily.adSlotId},"
			+ "		#{slotDaily.budgetId},"
			+ "		#{slotDaily.maxRequests},"
			+ "		#{slotDaily.requestCount},"
			+ "		#{slotDaily.fillCount},"
			+ "		#{slotDaily.displayCount},"
			+ "		#{slotDaily.clickCount},"
			+ "		#{slotDaily.downloadCount},"
			+ "		#{slotDaily.installCount},"
			+ "		#{slotDaily.deeplinkCount},"
			+ "		#{slotDaily.sspEstimateIncome},"
			+ "		#{slotDaily.dspEstimateIncome},"
			+ "		#{slotDaily.date}"
			+ ")")
	int saveSlotDaily(@Param("slotDaily") DataStatisticsSlotDaily slotDaily);
	
	@Update(" 	UPDATE adx_data_statistics_slot_daily "
			+ "	SET frequest_count = #{slotDaily.requestCount},"
			+ "		ffill_count = #{slotDaily.fillCount},"
			+ "		fdisplay_count = #{slotDaily.displayCount},"
			+ "		fclick_count = #{slotDaily.clickCount},"
			+ "		fdownload_count = #{slotDaily.downloadCount},"
			+ "		finstall_count = #{slotDaily.installCount},"
			+ "		fdeeplink_count = #{slotDaily.deeplinkCount}"
			+ "	WHERE fdate = #{slotDaily.date} "
			+ "		AND fad_slot_id = #{slotDaily.adSlotId} "
			+ "		AND fbudget_id = #{slotDaily.budgetId} ")
	int updateSlotDaily(@Param("slotDaily") DataStatisticsSlotDaily slotDaily);

	@Update(" 	UPDATE adx_data_statistics_slot_daily "
			+ "	SET fssp_estimate_income = #{slotDaily.sspEstimateIncome},"
			+ "		fdsp_estimate_income = #{slotDaily.dspEstimateIncome}"
			+ "	WHERE fdate = #{slotDaily.date}"
			+ "		AND fad_slot_id = #{slotDaily.adSlotId} "
			+ "		AND fbudget_id = #{slotDaily.budgetId} ")
	int updateSlotDailyIncome(@Param("slotDaily") DataStatisticsSlotDaily slotDaily);

	

}
