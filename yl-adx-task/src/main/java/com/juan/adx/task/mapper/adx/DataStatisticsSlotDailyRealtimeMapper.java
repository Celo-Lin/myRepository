package com.juan.adx.task.mapper.adx;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.juan.adx.model.entity.manage.DataStatisticsSlotDaily;
import com.juan.adx.model.entity.manage.DataStatisticsSlotRealtime;
import com.juan.adx.model.entity.manage.DataStatisticsSlotDailySsp;
import com.juan.adx.model.entity.manage.DataStatisticsSlotMonitoring;
import com.juan.adx.model.entity.manage.DataStatisticsTrendIndex;
import com.juan.adx.model.entity.sspmanage.ChannelDataStatisticsTrendIndex;
import com.juan.adx.model.form.task.SlotStatisticsDataForm;

@Mapper
public interface DataStatisticsSlotDailyRealtimeMapper {

	String SQL_COLUMN = "	fid AS id, "
			+ "				fssp_partner_id AS sspPartnerId, "
			+ "				fdsp_partner_id AS dspPartnerId, "
			+ "				fapp_id AS appId, "
			+ "				fad_slot_id AS adSlotId, "
			+ "				fbudget_id AS budgetId, "
			+ "				fad_slot_type AS adSlotType, "
			+ "				fmax_requests AS maxRequests, "
			+ "				frequest_count AS requestCount, "
			+ "				ffill_count AS fillCount, "
			+ "				fdisplay_count AS displayCount, "
			+ "				fclick_count AS clickCount, "
			+ "				fdownload_count AS downloadCount, "
			+ "				finstall_count AS installCount, "
			+ "				fdeeplink_count AS deeplinkCount, "
			+ "				fssp_estimate_income AS sspEstimateIncome, "
			+ "				fdsp_estimate_income AS dspEstimateIncome, "
			+ "				fcooperation_mode AS cooperationMode, "
			+ "				fdate AS date ";

	@Select("	<script> 	"
			+ "		SELECT " + SQL_COLUMN
			+ "		FROM adx_data_statistics_slot_realtime "
			+ "	<where> "
			+ "		<if test=\"form.hourSeconds != null and form.hourSeconds > 0\"> AND fdate = #{form.hourSeconds} </if> "
			+ "		<if test=\"form.mod != null and form.mod > 0\"> AND fcooperation_mode = #{form.mod} </if> "
			+ "		<if test=\"form.slotId != null and form.slotId > 0\"> AND fad_slot_id = #{form.slotId} </if> "
			+ "		<if test=\"form.budgetId != null and form.budgetId > 0\"> AND fbudget_id = #{form.budgetId} </if> "
			+ " </where>"			
			+ "	</script>")
	DataStatisticsSlotRealtime querySlotRealtime(@Param("form") SlotStatisticsDataForm form);
	
	@Select(" 	SELECT 	fssp_partner_id AS sspPartnerId,"
			+ "			fdsp_partner_id AS dspPartnerId, "
			+ "			fapp_id AS appId, "
			+ "			fad_slot_id AS adSlotId, "
			+ "			fbudget_id AS budgetId, "
			+ "			fmax_requests AS maxRequests, "
			+ "			SUM(frequest_count) AS requestCount, "
			+ "			SUM(ffill_count) AS fillCount, "
			+ "			SUM(fdisplay_count) AS displayCount, "
			+ "			SUM(fclick_count) AS clickCount, "
			+ "			SUM(fdownload_count) AS downloadCount, "
			+ "			SUM(finstall_count) AS installCount, "
			+ "			SUM(fdeeplink_count) AS deeplinkCount"
			+ "	FROM adx_data_statistics_slot_realtime "
			+ "	WHERE fdate BETWEEN #{startTime} AND #{endTime} "
			+ "	GROUP BY fad_slot_id, fbudget_id " )
	List<DataStatisticsSlotDaily> querySlotDailyStatisticsData(@Param("startTime") Long startTime, @Param("endTime") Long endTime);
	
	
	@Select(" 	SELECT 	fssp_partner_id AS sspPartnerId,"
			+ "			fapp_id AS appId, "
			+ "			fad_slot_id AS adSlotId, "
			+ "			fmax_requests AS maxRequests, "
			+ "			SUM(frequest_count) AS requestCount, "
			+ "			SUM(ffill_count) AS fillCount, "
			+ "			SUM(fdisplay_count) AS displayCount, "
			+ "			SUM(fclick_count) AS clickCount, "
			+ "			SUM(fdownload_count) AS downloadCount, "
			+ "			SUM(finstall_count) AS installCount, "
			+ "			SUM(fdeeplink_count) AS deeplinkCount"
			+ "	FROM adx_data_statistics_slot_realtime "
			+ "	WHERE fdate BETWEEN #{startTime} AND #{endTime} "
			+ "	GROUP BY fssp_partner_id, fad_slot_id " )
	List<DataStatisticsSlotDailySsp> querySlotDailySspStatisticsData(@Param("startTime") Long startTime, @Param("endTime") Long endTime);
	

	@Select(" 	SELECT 	fad_slot_type AS adSlotType, "
			+ "			SUM(frequest_count) AS requestCount, "
			+ "			SUM(ffill_count) AS fillCount, "
			+ "			SUM(fdisplay_count) AS displayCount, "
			+ "			SUM(fclick_count) AS clickCount "
			+ "	FROM adx_data_statistics_slot_realtime "
			+ "	WHERE fdate BETWEEN #{startTime} AND #{endTime} "
			+ "	GROUP BY fad_slot_type " )
	List<DataStatisticsTrendIndex> queryIndexTrendStatisticsData(@Param("startTime") Long startTime, @Param("endTime") Long endTime);
	
	
	@Select(" 	SELECT 	fssp_partner_id AS sspPartnerId, "
			+ "			fad_slot_type AS adSlotType, "
			+ "			SUM(ffill_count) AS fillCount, "
			+ "			SUM(fdisplay_count) AS displayCount "
			+ "	FROM adx_data_statistics_slot_realtime "
			+ "	WHERE fdate BETWEEN #{startTime} AND #{endTime} "
			+ "	GROUP BY fssp_partner_id, fad_slot_type " )
	List<ChannelDataStatisticsTrendIndex> queryIndexTrendSspStatisticsData(@Param("startTime") Long startTime, @Param("endTime") Long endTime);

	@Select(" 	SELECT 	fad_slot_id AS adSlotId, "
			+ "			SUM(frequest_count) AS requestCount, "
			+ "			SUM(ffill_count) AS fillCount, "
			+ "			SUM(fdisplay_count) AS displayCount, "
			+ "			SUM(fclick_count) AS clickCount "
			+ "	FROM adx_data_statistics_slot_realtime "
			+ "	WHERE fdate BETWEEN #{startTime} AND #{endTime} "
			+ "	GROUP BY fad_slot_id " )
	List<DataStatisticsSlotMonitoring> querySlotMonitoringStatisticsData(@Param("startTime") Long startTime, @Param("endTime") Long endTime);
	
	@Insert(" 	INSERT INTO adx_data_statistics_slot_realtime ("
			+ "		fssp_partner_id,"
			+ "		fdsp_partner_id,"
			+ "		fapp_id,"
			+ "		fad_slot_id,"
			+ "		fbudget_id,"
			+ "		fad_slot_type,"
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
			+ "		fcooperation_mode,"
			+ "		fdate"
			+ "	) VALUES ("
			+ "		#{slotRealtime.sspPartnerId},"
			+ "		#{slotRealtime.dspPartnerId},"
			+ "		#{slotRealtime.appId},"
			+ "		#{slotRealtime.adSlotId},"
			+ "		#{slotRealtime.budgetId},"
			+ "		#{slotRealtime.adSlotType},"
			+ "		#{slotRealtime.maxRequests},"
			+ "		#{slotRealtime.requestCount},"
			+ "		#{slotRealtime.fillCount},"
			+ "		#{slotRealtime.displayCount},"
			+ "		#{slotRealtime.clickCount},"
			+ "		#{slotRealtime.downloadCount},"
			+ "		#{slotRealtime.installCount},"
			+ "		#{slotRealtime.deeplinkCount},"
			+ "		#{slotRealtime.sspEstimateIncome},"
			+ "		#{slotRealtime.dspEstimateIncome},"
			+ "		#{slotRealtime.cooperationMode},"
			+ "		#{slotRealtime.date}"
			+ ") ON DUPLICATE KEY UPDATE fmax_requests = VALUES(fmax_requests),"
			+ "		ffill_count = VALUES(ffill_count),"
			+ "		fdisplay_count = VALUES(fdisplay_count),"
			+ "		fclick_count = VALUES(fclick_count),"
			+ "		fdownload_count = VALUES(fdownload_count),"
			+ "		finstall_count = VALUES(finstall_count),"
			+ "		fdeeplink_count = VALUES(fdeeplink_count) ")
	int saveSlotRealtimeStatistics(@Param("slotRealtime") DataStatisticsSlotRealtime slotRealtime);
	
	@Update(" 	UPDATE adx_data_statistics_slot_realtime "
			+ "	SET frequest_count = #{slotRealtime.requestCount},"
			+ "		ffill_count = #{slotRealtime.fillCount},"
			+ "		fdisplay_count = #{slotRealtime.displayCount},"
			+ "		fclick_count = #{slotRealtime.clickCount},"
			+ "		fdownload_count = #{slotRealtime.downloadCount},"
			+ "		finstall_count = #{slotRealtime.installCount},"
			+ "		fdeeplink_count = #{slotRealtime.deeplinkCount}"
			+ "	WHERE fid = #{slotRealtime.id} ")
	int updateSlotRealtimeStatistics(@Param("slotRealtime") DataStatisticsSlotRealtime slotRealtime);

	@Insert(" 	INSERT INTO adx_data_statistics_slot_realtime ("
			+ "		fssp_partner_id,"
			+ "		fdsp_partner_id,"
			+ "		fapp_id,"
			+ "		fad_slot_id,"
			+ "		fbudget_id,"
			+ "		fad_slot_type,"
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
			+ "		fcooperation_mode,"
			+ "		fdate"
			+ "	) VALUES ("
			+ "		#{slotRealtime.sspPartnerId},"
			+ "		#{slotRealtime.dspPartnerId},"
			+ "		#{slotRealtime.appId},"
			+ "		#{slotRealtime.adSlotId},"
			+ "		#{slotRealtime.budgetId},"
			+ "		#{slotRealtime.adSlotType},"
			+ "		#{slotRealtime.maxRequests},"
			+ "		#{slotRealtime.requestCount},"
			+ "		#{slotRealtime.fillCount},"
			+ "		#{slotRealtime.displayCount},"
			+ "		#{slotRealtime.clickCount},"
			+ "		#{slotRealtime.downloadCount},"
			+ "		#{slotRealtime.installCount},"
			+ "		#{slotRealtime.deeplinkCount},"
			+ "		#{slotRealtime.sspEstimateIncome},"
			+ "		#{slotRealtime.dspEstimateIncome},"
			+ "		#{slotRealtime.cooperationMode},"
			+ "		#{slotRealtime.date}"
			+ ") ON DUPLICATE KEY UPDATE fssp_estimate_income = VALUES(fssp_estimate_income),"
			+ "		fdsp_estimate_income = VALUES(fdsp_estimate_income) ")
	int saveSlotRealtimeIncome(@Param("slotRealtime") DataStatisticsSlotRealtime slotRealtime);
	
	
	@Update(" 	UPDATE adx_data_statistics_slot_realtime "
			+ "	SET fssp_estimate_income = #{slotRealtime.sspEstimateIncome},"
			+ "		fdsp_estimate_income = #{slotRealtime.dspEstimateIncome}"
			+ "	WHERE fdate = #{slotRealtime.date}"
			+ "		AND fad_slot_id = #{slotRealtime.adSlotId} "
			+ "		AND fbudget_id = #{slotRealtime.budgetId}"
			+ "		AND fcooperation_mode = #{slotRealtime.cooperationMode} ")
	int updateSlotRealtimeIncome(@Param("slotRealtime") DataStatisticsSlotRealtime slotRealtime);

	
	
}
