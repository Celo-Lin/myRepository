package com.juan.adx.manage.mapper.adx;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.juan.adx.model.dto.manage.DataStatisticsSlotMonitoringDto;
import com.juan.adx.model.form.manage.DataStatisticsSlotMonitoringForm;

@Mapper
public interface DataStatisticsSlotMonitoringMapper {

	@Select("	<script>"
			+ "		SELECT 	SLOT_MONITORING.fid AS id,"
			+ "				SLOT_MONITORING.fad_slot_id AS adSlotId,"
			+ "				SLOT_MONITORING.frequest_count AS requestCount,"
			+ "				SLOT_MONITORING.ffill_count AS fillCount,"
			+ "				SLOT_MONITORING.fdisplay_count AS displayCount,"
			+ "				SLOT_MONITORING.fclick_count AS clickCount,"
			+ "				SLOT_MONITORING.flast_request_time AS lastRequestTime,"
			+ "				SLOT_MONITORING.flast_fill_time AS lastFillTime,"
			+ "				SLOT_MONITORING.flast_display_time AS lastDisplayTime,"
			+ "				SLOT_MONITORING.flast_click_time AS lastClickTime,"
			+ "				SLOT_MONITORING.fdate AS date,"
			+ "				SSP_PARTNER.fname AS sspPartnerName,"
			+ "				APP.fname AS appName,"
			+ "				SLOT.fname AS adSlotName "
			+ "		FROM adx_data_statistics_slot_monitoring AS SLOT_MONITORING "
			+ "			LEFT JOIN adx_ssp_ad_slot AS SLOT ON SLOT_MONITORING.fad_slot_id = SLOT.fid "
			+ "			LEFT JOIN adx_ssp_app AS APP ON SLOT.fapp_id = APP.fid "
			+ "			LEFT JOIN adx_ssp_partner AS SSP_PARTNER ON SLOT.fssp_partner_id = SSP_PARTNER.fid "
			+ "		<where> "
			+ "			<if test=\"form.sspPartnerId != null and form.sspPartnerId > 0 \"> "
			+ "				AND SSP_PARTNER.fid = #{form.sspPartnerId} "
			+ "			</if> "
			+ "			<if test=\"form.appName != null and form.appName != '' \"> "
			+ "				AND APP.fname like concat('%', #{form.appName},'%') "
			+ "			</if> "
			+ "			<if test=\"form.adSlotId != null and form.adSlotId > 0 \"> "
			+ "				AND SLOT_MONITORING.fad_slot_id = #{form.adSlotId} "
			+ "			</if> "
			+ "			<if test=\"form.startTime != null and form.startTime > 0 and form.endTime != null and form.endTime > 0 \"> "
			+ "				AND SLOT_MONITORING.fdate BETWEEN #{form.startTime} AND #{form.endTime} "
			+ "			</if> "
			+ "		</where> "
			+ "		ORDER BY SLOT.fctime DESC, SLOT_MONITORING.flast_request_time DESC "
			+ "	</script> ")
	List<DataStatisticsSlotMonitoringDto> querySlotMonitoringDataList(@Param("form") DataStatisticsSlotMonitoringForm form);

}
