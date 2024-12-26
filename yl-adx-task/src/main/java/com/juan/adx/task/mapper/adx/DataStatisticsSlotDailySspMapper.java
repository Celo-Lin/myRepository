package com.juan.adx.task.mapper.adx;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.juan.adx.model.entity.manage.DataStatisticsSlotDailySsp;

@Mapper
public interface DataStatisticsSlotDailySspMapper {
	

	@Select("	SELECT SUM(fssp_estimate_income)"
			+ "	FROM adx_data_statistics_slot_daily_ssp "
			+ "	WHERE fdate BETWEEN #{startTime} AND #{endTime} AND fssp_partner_id = #{sspPartnerId} ")
	Long statisticsSspEstimateIncome(@Param("sspPartnerId") int sspPartnerId, @Param("startTime") long startTime, @Param("endTime") long endTime);

	@Insert(" 	INSERT IGNORE INTO adx_data_statistics_slot_daily_ssp ("
			+ "		fssp_partner_id,"
			+ "		fapp_id,"
			+ "		fad_slot_id,"
			+ "		fmax_requests,"
			+ "		frequest_count,"
			+ "		ffill_count,"
			+ "		fdisplay_count,"
			+ "		fclick_count,"
			+ "		fdownload_count,"
			+ "		finstall_count,"
			+ "		fdeeplink_count,"
			+ "		fssp_estimate_income,"
			+ "		faudit_status,"
			+ "		fdate"
			+ "	) VALUES ("
			+ "		#{slotDailySsp.sspPartnerId},"
			+ "		#{slotDailySsp.appId},"
			+ "		#{slotDailySsp.adSlotId},"
			+ "		#{slotDailySsp.maxRequests},"
			+ "		#{slotDailySsp.requestCount},"
			+ "		#{slotDailySsp.fillCount},"
			+ "		#{slotDailySsp.displayCount},"
			+ "		#{slotDailySsp.clickCount},"
			+ "		#{slotDailySsp.downloadCount},"
			+ "		#{slotDailySsp.installCount},"
			+ "		#{slotDailySsp.deeplinkCount},"
			+ "		#{slotDailySsp.sspEstimateIncome},"
			+ "		#{slotDailySsp.auditStatus},"
			+ "		#{slotDailySsp.date}"
			+ ")")
	int saveSlotDailySsp(@Param("slotDailySsp") DataStatisticsSlotDailySsp slotDailySsp);
	
	@Update(" 	UPDATE adx_data_statistics_slot_daily_ssp "
			+ "	SET frequest_count = #{slotDailySsp.requestCount},"
			+ "		ffill_count = #{slotDailySsp.fillCount},"
			+ "		fdisplay_count = #{slotDailySsp.displayCount},"
			+ "		fclick_count = #{slotDailySsp.clickCount},"
			+ "		fdownload_count = #{slotDailySsp.downloadCount},"
			+ "		finstall_count = #{slotDailySsp.installCount},"
			+ "		fdeeplink_count = #{slotDailySsp.deeplinkCount}"
			+ "	WHERE fssp_partner_id = #{slotDailySsp.sspPartnerId} "
			+ "		AND fdate = #{slotDailySsp.date} "
			+ "		AND fad_slot_id = #{slotDailySsp.adSlotId} ")
	int updateSlotDailySsp(@Param("slotDailySsp") DataStatisticsSlotDailySsp slotDailySsp);

	
	@Update(" 	UPDATE adx_data_statistics_slot_daily_ssp "
			+ "	SET fssp_estimate_income = #{slotDailySsp.sspEstimateIncome}"
			+ "	WHERE fssp_partner_id = #{slotDailySsp.sspPartnerId} "
			+ "		AND fdate = #{slotDailySsp.date}"
			+ "		AND fad_slot_id = #{slotDailySsp.adSlotId} ")
	int updateSlotDailySspIncome(@Param("slotDailySsp") DataStatisticsSlotDailySsp slotDailySsp);


}
