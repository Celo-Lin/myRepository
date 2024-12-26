package com.juan.adx.manage.mapper.adx;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.juan.adx.model.dto.manage.SspAdvertSlotDto;
import com.juan.adx.model.entity.manage.SspAdvertSlot;
import com.juan.adx.model.form.manage.SspAdvertSlotForm;

@Mapper
public interface SspAdvertSlotMapper {

	@Select("	SELECT COUNT(1) "
			+ "	FROM adx_ssp_ad_slot "
			+ "	WHERE fapp_id = #{appId}")
	int countSlotByAppid(@Param("appId") Integer appId);

	@Select("	<script>"
			+ "		SELECT 	SLOT.fid AS id,"
			+ "				SLOT.fname AS name,"
			+ "				SLOT.fssp_partner_id AS sspPartnerId,"
			+ "				SLOT.fapp_id AS appId,"
			+ "				SLOT.ftype AS type,"
			+ "				SLOT.fintegration_mode AS integrationMode,"
			+ "				SLOT.fcooperation_mode AS cooperationMode,"
			+ "				SLOT.fssp_bid_price AS sspBidPrice,"
			+ "				SLOT.fstatus AS status,"
			+ "				SLOT.fremarks AS remarks,"
			+ "				SLOT.fctime AS ctime,"
			+ "				SLOT.futime AS utime,"
			+ "				PARTNER.fname AS sspPartnerName,"
			+ "				APP.fsystem_platform AS systemPlatform, "
			+ "				APP.fname AS appName "
			+ "		FROM adx_ssp_ad_slot AS SLOT LEFT JOIN adx_ssp_app AS APP ON SLOT.fapp_id = APP.fid"
			+ "			 LEFT JOIN adx_ssp_partner AS PARTNER ON SLOT.fssp_partner_id = PARTNER.fid "
			+ "		<where> "
			+ "			<if test=\"form.sspPartnerId != null and form.sspPartnerId > 0 \"> "
			+ "				AND SLOT.fssp_partner_id = #{form.sspPartnerId} "
			+ "			</if> "
			+ "			<if test=\"form.slotId != null and form.slotId > 0 \"> "
			+ "				AND SLOT.fid = #{form.slotId} "
			+ "			</if> "
			+ "			<if test=\"form.appId != null and form.appId > 0 \"> "
			+ "				AND SLOT.fapp_id = #{form.appId} "
			+ "			</if> "
			+ "			<if test=\"form.advertType != null and form.advertType > 0 \"> "
			+ "				AND SLOT.ftype = #{form.advertType} "
			+ "			</if> "
			+ "			<if test=\"form.name != null and form.name != '' \"> "
			+ "				AND SLOT.fname like concat(#{form.name},'%')"
			+ "			</if> "
			+ "		</where> "
			+ "		ORDER BY SLOT.fid DESC "
			+ "	</script> ")
	List<SspAdvertSlotDto> querySspAdvertSlotList(@Param("form") SspAdvertSlotForm form);
	
	@Select("	SELECT 	SLOT.fid AS id,"
			+ "			SLOT.fname AS name,"
			+ "			SLOT.fssp_partner_id AS sspPartnerId,"
			+ "			SLOT.fapp_id AS appId,"
			+ "			SLOT.ftype AS type,"
			+ "			SLOT.fintegration_mode AS integrationMode,"
			+ "			SLOT.fcooperation_mode AS cooperationMode,"
			+ "			SLOT.fssp_bid_price AS sspBidPrice,"
			+ "			SLOT.fstatus AS status,"
			+ "			SLOT.fremarks AS remarks,"
			+ "			SLOT.fctime AS ctime,"
			+ "			SLOT.futime AS utime,"
			+ "			PARTNER.fname AS sspPartnerName,"
			+ "			APP.fname AS appName "
			+ "	FROM adx_ssp_ad_slot AS SLOT LEFT JOIN adx_ssp_app AS APP ON SLOT.fapp_id = APP.fid"
			+ "		 LEFT JOIN adx_ssp_partner AS PARTNER ON SLOT.fssp_partner_id = PARTNER.fid "
			+ "	WHERE SLOT.fid = #{id} ")
	SspAdvertSlotDto querySspAdvertSlot(@Param("id") Integer id);

	@Insert("	INSERT INTO adx_ssp_ad_slot("
			+ "		fid,"
			+ "		fssp_partner_id,"
			+ "		fapp_id,"
			+ "		fname,"
			+ "		ftype,"
			+ "		fintegration_mode,"
			+ "		fcooperation_mode,"
			+ "		fssp_bid_price,"
			+ "		fstatus,"
			+ "		fremarks,"
			+ "		fctime,"
			+ "		futime"
			+ "	) VALUES ("
			+ "		#{advertSlot.id},"
			+ "		#{advertSlot.sspPartnerId},"
			+ "		#{advertSlot.appId},"
			+ "		#{advertSlot.name},"
			+ "		#{advertSlot.type},"
			+ "		#{advertSlot.integrationMode},"
			+ "		#{advertSlot.cooperationMode},"
			+ "		#{advertSlot.sspBidPrice},"
			+ "		#{advertSlot.status},"
			+ "		#{advertSlot.remarks},"
			+ "		#{advertSlot.ctime},"
			+ "		#{advertSlot.utime}"
			+ ")")
	int saveSspAdvertSlot(@Param("advertSlot") SspAdvertSlot advertSlot);

	@Update(" 	UPDATE adx_ssp_ad_slot "
			+ "	SET fname = #{advertSlot.name},"
			+ "		ftype = #{advertSlot.type},"
			+ "		fintegration_mode = #{advertSlot.integrationMode},"
			+ "		fcooperation_mode = #{advertSlot.cooperationMode},"
			+ "		fssp_bid_price = #{advertSlot.sspBidPrice},"
			+ "		fremarks = #{advertSlot.remarks},"
			+ "		futime = #{advertSlot.utime}"
			+ "	WHERE fid = #{advertSlot.id}")
	int updateSspAdvertSlot(@Param("advertSlot") SspAdvertSlot advertSlot);
	
	@Update(" 	UPDATE adx_ssp_ad_slot "
			+ "	SET fname = #{advertSlot.name},"
			+ "		ftype = #{advertSlot.type},"
			+ "		fintegration_mode = #{advertSlot.integrationMode},"
			+ "		futime = #{advertSlot.utime}"
			+ "	WHERE fid = #{advertSlot.id}")
	int syncChannelUpdateToSspAdvertSlot(@Param("advertSlot") SspAdvertSlot advertSlot);

	@Update("	UPDATE adx_ssp_ad_slot "
			+ "	SET fstatus = #{status}"
			+ "	WHERE fid = #{id}")
	int updateSspAdvertSlotStatus(@Param("id") Integer id, @Param("status") Integer status);

	@Delete("DELETE FROM adx_ssp_ad_slot WHERE fid = #{id}")
	int deleteSspAdvertSlot(@Param("id") Integer id);


}
