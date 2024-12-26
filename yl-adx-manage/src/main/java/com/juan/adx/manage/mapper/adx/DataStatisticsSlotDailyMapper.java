package com.juan.adx.manage.mapper.adx;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.juan.adx.model.dto.manage.DataStatisticsSlotDailyDto;
import com.juan.adx.model.dto.manage.ImportSlotDailyDto;
import com.juan.adx.model.entity.manage.DataStatisticsSlotDaily;
import com.juan.adx.model.form.manage.DataStatisticsSlotDailyForm;

@Mapper
public interface DataStatisticsSlotDailyMapper {

	@Select("	<script>"
			+ "		SELECT 	SLOT_DAILY.fid AS id,"
			+ "				SLOT_DAILY.fssp_partner_id AS sspPartnerId,"
			+ "				SLOT_DAILY.fdsp_partner_id AS dspPartnerId,"
			+ "				SLOT_DAILY.fapp_id AS appId,"
			+ "				SLOT_DAILY.fad_slot_id AS adSlotId,"
			+ "				SLOT_DAILY.fbudget_id AS budgetId,"
			+ "				SLOT_DAILY.fmax_requests AS maxRequests,"
			+ "				SLOT_DAILY.frequest_count AS requestCount,"
			+ "				SLOT_DAILY.ffill_count AS fillCount,"
			+ "				SLOT_DAILY.fdisplay_count AS displayCount,"
			+ "				SLOT_DAILY.fclick_count AS clickCount,"
			+ "				SLOT_DAILY.fdownload_count AS downloadCount,"
			+ "				SLOT_DAILY.finstall_count AS installCount,"
			+ "				SLOT_DAILY.fdeeplink_count AS deeplinkCount,"
			+ "				SLOT_DAILY.fssp_estimate_income AS sspEstimateIncome,"
			+ "				SLOT_DAILY.fdsp_estimate_income AS dspEstimateIncome,"
			+ "				SLOT_DAILY.fdate AS date,"
			+ "				SSP_PARTNER.fname AS sspPartnerName,"
			+ "				DSP_PARTNER.fname AS dspPartnerName,"
			+ "				APP.fname AS appName,"
			+ "				SLOT.fname AS adSlotName, "
			+ "				SLOT.fcooperation_mode AS cooperationMode, "
			+ "				BUDGET.fname AS budgetName "
			+ "		FROM adx_data_statistics_slot_daily AS SLOT_DAILY LEFT JOIN adx_dsp_partner AS DSP_PARTNER ON SLOT_DAILY.fdsp_partner_id = DSP_PARTNER.fid "
			+ "			LEFT JOIN adx_ssp_partner AS SSP_PARTNER ON SLOT_DAILY.fssp_partner_id = SSP_PARTNER.fid "
			+ "			LEFT JOIN adx_ssp_app AS APP ON SLOT_DAILY.fapp_id = APP.fid "
			+ "			LEFT JOIN adx_ssp_ad_slot AS SLOT ON SLOT_DAILY.fad_slot_id = SLOT.fid "
			+ "			LEFT JOIN adx_dsp_budget AS BUDGET ON  SLOT_DAILY.fbudget_id = BUDGET.fid "
			+ "		<where> "
			+ "			<if test=\"form.dspPartnerId != null and form.dspPartnerId > 0 \"> "
			+ "				AND SLOT_DAILY.fdsp_partner_id = #{form.dspPartnerId} "
			+ "			</if> "
			+ "			<if test=\"form.sspPartnerId != null and form.sspPartnerId > 0 \"> "
			+ "				AND SLOT_DAILY.fssp_partner_id = #{form.sspPartnerId} "
			+ "			</if> "
			+ "			<if test=\"form.adSlotId != null and form.adSlotId > 0 \"> "
			+ "				AND SLOT_DAILY.fad_slot_id = #{form.adSlotId} "
			+ "			</if> "
			+ "			<if test=\"form.startTime != null and form.startTime > 0 and form.endTime != null and form.endTime > 0 \"> "
			+ "				AND SLOT_DAILY.fdate BETWEEN #{form.startTime} AND #{form.endTime} "
			+ "			</if> "
			+ "			<if test=\"form.appName != null and form.appName != '' \"> "
			+ "				AND APP.fname like concat('%', #{form.appName},'%') "
			+ "			</if> "
			+ "			<if test=\"form.adSlotName != null and form.adSlotName != '' \"> "
			+ "				AND SLOT.fname like concat('%', #{form.adSlotName},'%')"
			+ "			</if> "
			+ "			<if test=\"form.cooperationMode != null and form.cooperationMode != '' \"> "
			+ "				AND SLOT.fcooperation_mode = #{form.cooperationMode}"
			+ "			</if> "
			+ "		</where> "
			+ "		ORDER BY SLOT_DAILY.fdate DESC, SLOT_DAILY.fid DESC "
			+ "	</script> ")
	List<DataStatisticsSlotDailyDto> queryDataStatementSlotDailyList(@Param("form") DataStatisticsSlotDailyForm form);

	@Select("	SELECT 	SLOT_DAILY.fid AS id,"
			+ "			SLOT_DAILY.fssp_partner_id AS sspPartnerId,"
			+ "			SLOT_DAILY.fdsp_partner_id AS dspPartnerId,"
			+ "			SLOT_DAILY.fapp_id AS appId,"
			+ "			SLOT_DAILY.fad_slot_id AS adSlotId,"
			+ "			SLOT_DAILY.fmax_requests AS maxRequests,"
			+ "			SLOT_DAILY.frequest_count AS requestCount,"
			+ "			SLOT_DAILY.ffill_count AS fillCount,"
			+ "			SLOT_DAILY.fdisplay_count AS displayCount,"
			+ "			SLOT_DAILY.fclick_count AS clickCount,"
			+ "			SLOT_DAILY.fdownload_count AS downloadCount,"
			+ "			SLOT_DAILY.finstall_count AS installCount,"
			+ "			SLOT_DAILY.fdeeplink_count AS deeplinkCount,"
			+ "			SLOT_DAILY.fssp_estimate_income AS sspEstimateIncome,"
			+ "			SLOT_DAILY.fdsp_estimate_income AS dspEstimateIncome,"
			+ "			SLOT_DAILY.fdate AS date,"
			+ "			SSP_PARTNER.fname AS sspPartnerName,"
			+ "			DSP_PARTNER.fname AS dspPartnerName,"
			+ "			APP.fname AS appName,"
			+ "			SLOT.fcooperation_mode AS cooperationMode, "
			+ "			SLOT.fname AS adSlotName "
			+ "	FROM adx_data_statistics_slot_daily AS SLOT_DAILY LEFT JOIN adx_dsp_partner AS DSP_PARTNER ON SLOT_DAILY.fdsp_partner_id = DSP_PARTNER.fid "
			+ "		LEFT JOIN adx_ssp_partner AS SSP_PARTNER ON SLOT_DAILY.fssp_partner_id = SSP_PARTNER.fid "
			+ "		LEFT JOIN adx_ssp_app AS APP ON SLOT_DAILY.fapp_id = APP.fid "
			+ "		LEFT JOIN adx_ssp_ad_slot AS SLOT ON SLOT_DAILY.fad_slot_id = SLOT.fid "
			+ "	WHERE SLOT_DAILY.fid = #{id} ")
	DataStatisticsSlotDailyDto queryDataStatementSlotDaily(@Param("id") Integer id);

	@Update("	UPDATE adx_data_statistics_slot_daily "
			+ "	SET frequest_count = #{slotDaily.requestCount},"
			+ "		ffill_count = #{slotDaily.fillCount},"
			+ "		fdisplay_count = #{slotDaily.displayCount},"
			+ "		fclick_count = #{slotDaily.clickCount},"
			+ "		fssp_estimate_income = #{slotDaily.sspEstimateIncome},"
			+ "		fdsp_estimate_income = #{slotDaily.dspEstimateIncome}"
			+ "	WHERE fid = #{slotDaily.id}")
	int updateDataStatementSlotDaily(@Param("slotDaily") DataStatisticsSlotDaily slotDaily);

	@Update("	UPDATE adx_data_statistics_slot_daily AS daily JOIN adx_ssp_ad_slot AS slot ON daily.fad_slot_id = slot.fid "
			+ "	SET daily.fssp_estimate_income = #{slotDaily.sspEstimateIncome},"
			+ "		daily.fdsp_estimate_income = #{slotDaily.dspEstimateIncome} "
			+ "	WHERE daily.fid = #{slotDaily.id} "
			+ "		AND slot.fcooperation_mode = #{slotDaily.cooperationMode} ")
	int updatePdSlotEstimateIncome(@Param("slotDaily") ImportSlotDailyDto slotDaily);

}
