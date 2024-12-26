package com.juan.adx.manage.mapper.adx;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.juan.adx.model.dto.manage.DataStatisticsSlotDailyRealtimeDto;
import com.juan.adx.model.form.manage.DataStatisticsSlotDailyRealtimeForm;

@Mapper
public interface DataStatisticsSlotDailyRealtimeMapper {

	@Select("	<script>"
			+ "		SELECT 	SLOT_REALTIME.fid AS id,"
			+ "				SLOT_REALTIME.fssp_partner_id AS sspPartnerId,"
			+ "				SLOT_REALTIME.fdsp_partner_id AS dspPartnerId,"
			+ "				SLOT_REALTIME.fapp_id AS appId,"
			+ "				SLOT_REALTIME.fad_slot_id AS adSlotId,"
			+ "				SLOT_REALTIME.fmax_requests AS maxRequests,"
			+ "				SLOT_REALTIME.frequest_count AS requestCount,"
			+ "				SLOT_REALTIME.ffill_count AS fillCount,"
			+ "				SLOT_REALTIME.fdisplay_count AS displayCount,"
			+ "				SLOT_REALTIME.fclick_count AS clickCount,"
			+ "				SLOT_REALTIME.fdownload_count AS downloadCount,"
			+ "				SLOT_REALTIME.finstall_count AS installCount,"
			+ "				SLOT_REALTIME.fdeeplink_count AS deeplinkCount,"
			+ "				SLOT_REALTIME.fssp_estimate_income AS sspEstimateIncome,"
			+ "				SLOT_REALTIME.fdsp_estimate_income AS dspEstimateIncome,"
			+ "				SLOT_REALTIME.fcooperation_mode AS cooperationMode,"
			+ "				SLOT_REALTIME.fdate AS date,"
			+ "				SSP_PARTNER.fname AS sspPartnerName,"
			+ "				DSP_PARTNER.fname AS dspPartnerName,"
			+ "				APP.fname AS appName,"
			+ "				SLOT.fname AS adSlotName, "
			+ "				BUDGET.fname AS budgetName "
			+ "		FROM adx_data_statistics_slot_realtime AS SLOT_REALTIME LEFT JOIN adx_dsp_partner AS DSP_PARTNER ON SLOT_REALTIME.fdsp_partner_id = DSP_PARTNER.fid "
			+ "			LEFT JOIN adx_ssp_partner AS SSP_PARTNER ON SLOT_REALTIME.fssp_partner_id = SSP_PARTNER.fid "
			+ "			LEFT JOIN adx_ssp_app AS APP ON SLOT_REALTIME.fapp_id = APP.fid "
			+ "			LEFT JOIN adx_ssp_ad_slot AS SLOT ON SLOT_REALTIME.fad_slot_id = SLOT.fid "
			+ "			LEFT JOIN adx_dsp_budget AS BUDGET ON  SLOT_REALTIME.fbudget_id = BUDGET.fid "
			+ "		WHERE SLOT_REALTIME.fcooperation_mode = #{form.cooperationMode}"
			+ "			<if test=\"form.dspPartnerId != null and form.dspPartnerId > 0 \"> "
			+ "				AND SLOT_REALTIME.fdsp_partner_id = #{form.dspPartnerId} "
			+ "			</if> "
			+ "			<if test=\"form.sspPartnerId != null and form.sspPartnerId > 0 \"> "
			+ "				AND SLOT_REALTIME.fssp_partner_id = #{form.sspPartnerId} "
			+ "			</if> "
			+ "			<if test=\"form.adSlotId != null and form.adSlotId > 0 \"> "
			+ "				AND SLOT_REALTIME.fad_slot_id = #{form.adSlotId} "
			+ "			</if> "
			+ "			<if test=\"form.startTime != null and form.startTime > 0 and form.endTime != null and form.endTime > 0 \"> "
			+ "				AND SLOT_REALTIME.fdate BETWEEN #{form.startTime} AND #{form.endTime} "
			+ "			</if> "
			+ "			<if test=\"form.appName != null and form.appName != '' \"> "
			+ "				AND APP.fname like concat('%', #{form.appName},'%') "
			+ "			</if> "
			+ "			<if test=\"form.adSlotName != null and form.adSlotName != '' \"> "
			+ "				AND (SLOT.fname like concat('%', #{form.adSlotName},'%') OR SLOT_REALTIME.fad_slot_id = #{form.adSlotName})"
			+ "			</if> "
			+ "		ORDER BY SLOT_REALTIME.fdate DESC, SLOT_REALTIME.fid DESC "
			+ "	</script> ")
	List<DataStatisticsSlotDailyRealtimeDto> queryDataStatementSlotDailyRealtimeList(@Param("form") DataStatisticsSlotDailyRealtimeForm form);

}
