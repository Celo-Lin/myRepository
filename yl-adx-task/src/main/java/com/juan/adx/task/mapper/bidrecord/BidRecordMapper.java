package com.juan.adx.task.mapper.bidrecord;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.juan.adx.model.entity.api.BidRecord;
import com.juan.adx.model.entity.task.StatisticsSlotIncomeDto;

@Mapper
public interface BidRecordMapper {
	
	String TABLE_ADX_AD_BID_RECORD = "adx_ad_bid_record";

	@Insert("	INSERT IGNORE INTO adx_ad_bid_record ("
			+ "		ftrace_id,"
			+ "		fssp_partner_id,"
			+ "		fdsp_partner_id,"
			+ "		fapp_id,"
			+ "		fslot_id,"
			+ "		fbudget_id,"
			+ "		fcooperation_mode,"
			+ "		fssp_bid_price,"
			+ "		fssp_return_price,"
			+ "		fdsp_bid_price,"
			+ "		fdsp_return_price,"
			+ "		freport_display_status,"
			+ "		fssp_floor_price_snapshot,"
			+ "		fdsp_floor_price_snapshot,"
			+ "		fssp_premium_rate_snapshot,"
			+ "		fdsp_floating_rate_snapshot,"
			+ "		fctime"
			+ "	) VALUES ("
			+ "		#{bidRecord.traceId},"
			+ "		#{bidRecord.sspPartnerId},"
			+ "		#{bidRecord.dspPartnerId},"
			+ "		#{bidRecord.appId},"
			+ "		#{bidRecord.slotId},"
			+ "		#{bidRecord.budgetId},"
			+ "		#{bidRecord.cooperationMode},"
			+ "		#{bidRecord.sspBidPrice},"
			+ "		#{bidRecord.sspReturnPrice},"
			+ "		#{bidRecord.dspBidPrice},"
			+ "		#{bidRecord.dspReturnPrice},"
			+ "		#{bidRecord.reportDisplayStatus},"
			+ "		#{bidRecord.sspFloorPriceSnapshot},"
			+ "		#{bidRecord.dspFloorPriceSnapshot},"
			+ "		#{bidRecord.sspPremiumRateSnapshot},"
			+ "		#{bidRecord.dspFloatingRateSnapshot},"
			+ "		#{bidRecord.ctime}"
			+ "	) ")
	int saveBidRecord(@Param("bidRecord") BidRecord bidRecord);

	
	@Update("	UPDATE adx_ad_bid_record "
			+ "	SET freport_display_status = #{status} "
			+ "	WHERE ftrace_id = #{traceId} "
			+ "		AND fctime BETWEEN #{startTime} AND #{endTime} ")
	int updateBidRecordDisplayStatus(@Param("traceId") Long traceId, @Param("status") Integer status, 
			@Param("startTime") Long startTime, @Param("endTime") Long endTime);


	@Select("	SELECT 	fslot_id AS slotId,"
			+ "			fbudget_id AS budgetId,"
			+ "			fcooperation_mode AS cooperationMode,"
			+ "			SUM(fdsp_return_price) AS dspEstimateIncome,"
			+ "			SUM(fssp_return_price) AS sspEstimateIncome "
			+ "	FROM adx_ad_bid_record "
			+ " WHERE fctime BETWEEN #{startTime} AND #{endTime} "
			+ "		AND fcooperation_mode IN (2, 3) "
			+ "		AND freport_display_status = 1 "
			+ "	GROUP BY fslot_id, fbudget_id ")
	List<StatisticsSlotIncomeDto> statisticsIncomeRealtime(@Param("startTime") long startTime, @Param("endTime") long endTime);

	@Select("	SELECT 	fslot_id AS slotId,"
			+ "			fbudget_id AS budgetId,"
			+ "			fcooperation_mode AS cooperationMode,"
			+ "			SUM(fdsp_return_price) AS dspEstimateIncome,"
			+ "			SUM(fssp_return_price) AS sspEstimateIncome "
			+ "	FROM adx_ad_bid_record "
			+ " WHERE fctime BETWEEN #{startTime} AND #{endTime}"
			+ "		AND fcooperation_mode IN (2, 3) "
			+ "		AND freport_display_status = 1 "
			+ "	GROUP BY fslot_id, fbudget_id ")
	List<StatisticsSlotIncomeDto> statisticsIncomeDaily(@Param("startTime") long startTime, @Param("endTime") long endTime);

	@Select("	SELECT 	fssp_partner_id AS sspPartnerId,"
			+ "			fslot_id AS slotId,"
			+ "			fcooperation_mode AS cooperationMode,"
			+ "			SUM(fssp_return_price) AS sspEstimateIncome "
			+ "	FROM adx_ad_bid_record "
			+ " WHERE fctime BETWEEN #{startTime} AND #{endTime}"
			+ "		AND fcooperation_mode IN (2, 3) "
			+ "		AND freport_display_status = 1 "
			+ "	GROUP BY fslot_id ")
	List<StatisticsSlotIncomeDto> statisticsIncomeDailySsp(@Param("startTime") long startTime, @Param("endTime") long endTime);

}
