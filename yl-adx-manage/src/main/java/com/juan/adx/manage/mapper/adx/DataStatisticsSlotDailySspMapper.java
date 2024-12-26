package com.juan.adx.manage.mapper.adx;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.juan.adx.model.dto.manage.DataStatisticsSlotDailySspDto;
import com.juan.adx.model.entity.manage.DataStatisticsSlotDailySsp;
import com.juan.adx.model.form.manage.DataStatisticsSlotDailySspForm;

@Mapper
public interface DataStatisticsSlotDailySspMapper {

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
			+ "				SLOT_DAILY_SSP.faudit_status AS auditStatus,"
			+ "				SSP_PARTNER.fname AS sspPartnerName,"
			+ "				APP.fname AS appName,"
			+ "				SLOT.fname AS adSlotName "
			+ "		FROM adx_data_statistics_slot_daily_ssp AS SLOT_DAILY_SSP LEFT JOIN adx_ssp_partner AS SSP_PARTNER ON SLOT_DAILY_SSP.fssp_partner_id = SSP_PARTNER.fid "
			+ "			LEFT JOIN adx_ssp_app AS APP ON SLOT_DAILY_SSP.fapp_id = APP.fid "
			+ "			LEFT JOIN adx_ssp_ad_slot AS SLOT ON SLOT_DAILY_SSP.fad_slot_id = SLOT.fid "
			+ "		<where> "
			+ "			<if test=\"form.sspPartnerId != null and form.sspPartnerId > 0 \"> "
			+ "				AND SLOT_DAILY_SSP.fssp_partner_id = #{form.sspPartnerId} "
			+ "			</if> "
			+ "			<if test=\"form.adSlotId != null and form.adSlotId > 0 \"> "
			+ "				AND SLOT_DAILY_SSP.fad_slot_id = #{form.adSlotId} "
			+ "			</if> "
			+ "			<if test=\"form.startTime != null and form.startTime > 0 and form.endTime != null and form.endTime > 0 \"> "
			+ "				AND SLOT_DAILY_SSP.fdate BETWEEN #{form.startTime} AND #{form.endTime} "
			+ "			</if> "
			+ "			<if test=\"form.appName != null and form.appName != '' \"> "
			+ "				AND APP.fname like concat('%', #{form.appName},'%') "
			+ "			</if> "
			+ "			<if test=\"form.adSlotName != null and form.adSlotName != '' \"> "
			+ "				AND SLOT.fname like concat('%', #{form.adSlotName},'%')"
			+ "			</if> "
			+ "		</where> "
			+ "		ORDER BY SLOT_DAILY_SSP.fdate DESC, SLOT_DAILY_SSP.fid DESC "
			+ "	</script> ")
	List<DataStatisticsSlotDailySspDto> queryDataStatementSlotDailySspList(@Param("form") DataStatisticsSlotDailySspForm form);

	@Update("	UPDATE adx_data_statistics_slot_daily_ssp "
			+ "	SET faudit_status = #{status}"
			+ "	WHERE fid = #{id} ")
	int updateAuditStatus(@Param("id") Integer id, @Param("status") Integer status);

	@Update("	UPDATE adx_data_statistics_slot_daily_ssp "
			+ "	SET frequest_count = #{slotDailySsp.requestCount},"
			+ "		ffill_count = #{slotDailySsp.fillCount},"
			+ "		fdisplay_count = #{slotDailySsp.displayCount},"
			+ "		fclick_count = #{slotDailySsp.clickCount},"
			+ "		fssp_estimate_income = #{slotDailySsp.sspEstimateIncome},"
			+ "		faudit_status = #{slotDailySsp.auditStatus}"
			+ "	WHERE fssp_partner_id = #{slotDailySsp.sspPartnerId} "
			+ "		AND fdate = #{slotDailySsp.date} "
			+ "		AND fad_slot_id = #{slotDailySsp.adSlotId} ")
	int updateDataStatementSlotDailySsp(@Param("slotDailySsp") DataStatisticsSlotDailySsp statisticsSlotDailySsp);

	@Update("	UPDATE adx_data_statistics_slot_daily_ssp "
			+ "	SET fssp_estimate_income = #{slotDailySsp.sspEstimateIncome},"
			+ "		faudit_status = #{slotDailySsp.auditStatus}"
			+ "	WHERE fssp_partner_id = #{slotDailySsp.sspPartnerId} "
			+ "		AND fdate = #{slotDailySsp.date} "
			+ "		AND fad_slot_id = #{slotDailySsp.adSlotId} ")
	int updatePdSlotEstimateIncomeSsp(@Param("slotDailySsp") DataStatisticsSlotDailySsp statisticsSlotDailySsp);

}
