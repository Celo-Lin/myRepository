package com.juan.adx.manage.mapper.adx;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.juan.adx.model.dto.manage.DingLiStatisticsDataDto;
import com.juan.adx.model.entity.manage.DingLiDataStatistics;
import com.juan.adx.model.entity.manage.DingLiDict;
import com.juan.adx.model.form.manage.DingLiDataStatisticsForm;

@Mapper
public interface DingLiDataMapper {

	@Select("	<script>"
			+ "		SELECT 	DS.fid AS id,"
			+ "				DS.fchannel_id AS channelId,"
			+ "				DS.fad_id AS adId,"
			+ "				CHANNEL.fname AS channelName,"
			+ "				AD.fname AS adName,"
			+ "				DS.fad_slot_name AS adSlotName,"
			+ "				DS.fad_slot_uuid AS adSlotUuid,"
			+ "				DS.fad_slot_type AS adSlotType,"
			+ "				DS.fdisplay_count AS displayCount,"
			+ "				DS.fclick_count AS clickCount,"
			+ "				DS.fclick_ratio AS clickRatio,"
			+ "				DS.fecpm AS ecmp,"
			+ "				DS.fcpc AS cpc,"
			+ "				DS.fssp_income AS sspIncome,"
			+ "				DS.fdsp_income AS dspIncome,"
			+ "				DS.fdate AS date"
			+ "		FROM ding_li_data_statistics AS DS LEFT JOIN ding_li_channel AS CHANNEL ON DS.fchannel_id = CHANNEL.fid "
			+ "			LEFT JOIN ding_li_ad AS AD ON DS.fad_id = AD.fid "
			+ "		<where> "
			+ "			<if test=\"form.adId != null and form.adId > 0 \"> "
			+ "				AND DS.fad_id = #{form.adId} "
			+ "			</if> "
			+ "			<if test=\"form.channelId != null and form.channelId > 0 \"> "
			+ "				AND DS.fchannel_id = #{form.channelId} "
			+ "			</if> "
			+ "			<if test=\"form.startTime != null and form.startTime > 0 and form.endTime != null and form.endTime > 0 \"> "
			+ "				AND DS.fdate BETWEEN #{form.startTime} AND #{form.endTime} "
			+ "			</if> "
			+ "		</where> "
			+ "		ORDER BY DS.fdate DESC "
			+ "	</script> ")
	List<DingLiStatisticsDataDto> queryDataStatement(@Param("form") DingLiDataStatisticsForm form);

	@Insert("INSERT IGNORE INTO ding_li_data_statistics("
			+ "	fchannel_id,"
			+ "	fad_id,"
			+ "	fad_slot_name,"
			+ "	fad_slot_uuid,"
			+ "	fad_slot_type,"
			+ "	fdisplay_count,"
			+ "	fclick_count,"
			+ "	fclick_ratio,"
			+ "	fecpm,"
			+ "	fcpc,"
			+ "	fssp_income,"
			+ "	fdsp_income,"
			+ "	fdate"
			+ ") VALUES ("
			+ "	#{ds.channelId},"
			+ "	#{ds.adId},"
			+ "	#{ds.adSlotName},"
			+ "	#{ds.adSlotUuid},"
			+ "	#{ds.adSlotType},"
			+ "	#{ds.displayCount},"
			+ "	#{ds.clickCount},"
			+ "	#{ds.clickRatio},"
			+ "	#{ds.ecmp},"
			+ "	#{ds.cpc},"
			+ "	#{ds.sspIncome},"
			+ "	#{ds.dspIncome},"
			+ "	#{ds.date}"
			+ ")")
	int saveDataStatement(@Param("ds") DingLiDataStatistics dataStatistics);

	@Select(" SELECT fid AS id, fname AS name FROM ding_li_channel ")
	List<DingLiDict> channels();

	@Select(" SELECT fid AS id, fname AS name FROM ding_li_ad ")
	List<DingLiDict> ads();

	@Update("UPDATE ding_li_data_statistics "
			+ " SET fad_slot_name = #{ds.adSlotName},"
			+ "	fad_slot_uuid = #{ds.adSlotUuid},"
			+ "	fad_slot_type = #{ds.adSlotType},"
			+ "	fdisplay_count = #{ds.displayCount},"
			+ "	fclick_count = #{ds.clickCount},"
			+ "	fclick_ratio = #{ds.clickRatio},"
			+ "	fecpm = #{ds.ecmp},"
			+ "	fcpc = #{ds.cpc},"
			+ "	fssp_income = #{ds.sspIncome},"
			+ "	fdsp_income = #{ds.dspIncome},"
			+ "	fdate = #{ds.date}"			
			+ "	WHERE fid = #{ds.id}")
	int updateDataStatement(@Param("ds") DingLiDataStatistics dataStatistics);
	
	@Update("DELETE FROM ding_li_data_statistics WHERE fid = #{id}")
	int deleteDataStatement(@Param("id") Integer id);

}
