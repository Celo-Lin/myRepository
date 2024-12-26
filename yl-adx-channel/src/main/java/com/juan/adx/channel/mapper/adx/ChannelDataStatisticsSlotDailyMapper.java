package com.juan.adx.channel.mapper.adx;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.juan.adx.model.dto.sspmanage.ChannelDataStatisticsSlotDailyDto;
import com.juan.adx.model.form.sspmanage.ChannelDataStatisticsSlotDailyForm;

@Mapper
public interface ChannelDataStatisticsSlotDailyMapper {

	@Select("	<script>"
			+ "		SELECT 	SLOT_DAILY_SSP.fid AS id,"
			+ "				SLOT_DAILY_SSP.fssp_partner_id AS sspPartnerId,"
			+ "				SLOT_DAILY_SSP.fapp_id AS appId,"
			+ "				SLOT_DAILY_SSP.fad_slot_id AS adSlotId,"
			+ "				SLOT_DAILY_SSP.fmax_requests AS maxRequests,"
			+ "				SLOT_DAILY_SSP.frequest_count AS requestCount,"
			+ "				SLOT_DAILY_SSP.ffill_count AS fillCount,"
			+ "				SLOT_DAILY_SSP.fdisplay_count AS displayCount,"
			+ "				SLOT_DAILY_SSP.fclick_count AS clickCount,"
			+ "				SLOT_DAILY_SSP.fdownload_count AS downloadCount,"
			+ "				SLOT_DAILY_SSP.finstall_count AS installCount,"
			+ "				SLOT_DAILY_SSP.fdeeplink_count AS deeplinkCount,"
			+ "				SLOT_DAILY_SSP.fssp_estimate_income AS sspEstimateIncome,"
			+ "				SLOT_DAILY_SSP.fdate AS date,"
			+ "				APP.fname AS appName,"
			+ "				SLOT.fname AS adSlotName "
			+ "		FROM adx_data_statistics_slot_daily_ssp AS SLOT_DAILY_SSP LEFT JOIN adx_ssp_app AS APP ON SLOT_DAILY_SSP.fapp_id = APP.fid "
			+ "			LEFT JOIN adx_ssp_ad_slot AS SLOT ON SLOT_DAILY_SSP.fad_slot_id = SLOT.fid "
			+ "		WHERE SLOT_DAILY_SSP.fssp_partner_id = #{form.sspPartnerId}"
			+ "				AND SLOT_DAILY_SSP.faudit_status = 2 "
			+ "			<if test=\"form.adSlotId != null and form.adSlotId > 0 \"> "
			+ "				AND SLOT_DAILY_SSP.fad_slot_id = #{form.adSlotId} "
			+ "			</if> "
			+ "			<if test=\"form.startTime != null and form.startTime > 0 and form.endTime != null and form.endTime > 0 \"> "
			+ "				AND SLOT_DAILY_SSP.fdate BETWEEN #{form.startTime} AND #{form.endTime} "
			+ "			</if> "
			+ "			<if test=\"form.appName != null and form.appName != '' \"> "
			+ "				AND APP.fname like concat(#{form.appName},'%') "
			+ "			</if> "
			+ "			<if test=\"form.adSlotName != null and form.adSlotName != '' \"> "
			+ "				AND SLOT.fname like concat(#{form.adSlotName},'%')"
			+ "			</if> "
			+ "		ORDER BY SLOT_DAILY_SSP.fdate DESC, SLOT_DAILY_SSP.fid DESC "
			+ "	</script> ")
	List<ChannelDataStatisticsSlotDailyDto> querySspDataStatementSlotDailyList(@Param("form") ChannelDataStatisticsSlotDailyForm form);

}
