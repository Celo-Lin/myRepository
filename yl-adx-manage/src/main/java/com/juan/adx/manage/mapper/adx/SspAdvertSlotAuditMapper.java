package com.juan.adx.manage.mapper.adx;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.juan.adx.model.dto.manage.SspAdvertSlotAuditDto;
import com.juan.adx.model.entity.manage.SspAdvertSlotAudit;
import com.juan.adx.model.form.manage.SspAdvertSlotForm;

@Mapper
public interface SspAdvertSlotAuditMapper {
	
	@Select("	<script>"
			+ "		SELECT 	SLOT.fid AS id,"
			+ "				SLOT.fname AS name,"
			+ "				SLOT.fssp_partner_id AS sspPartnerId,"
			+ "				SLOT.fapp_id AS appId,"
			+ "				SLOT.ftype AS type,"
			+ "				SLOT.fintegration_mode AS integrationMode,"
			+ "				SLOT.fremarks AS remarks,"
			+ "				SLOT.faudit_status AS auditStatus,"
			+ "				SLOT.faudit_comments AS auditComments,"
			+ "				SLOT.fctime AS ctime,"
			+ "				SLOT.futime AS utime,"
			+ "				PARTNER.fname AS sspPartnerName,"
			+ "				APP.fsystem_platform AS systemPlatform, "
			+ "				APP.fname AS appName "
			+ "		FROM adx_ssp_ad_slot_audit AS SLOT LEFT JOIN adx_ssp_app_audit AS APP ON SLOT.fapp_id = APP.fid"
			+ "			 LEFT JOIN adx_ssp_partner AS PARTNER ON SLOT.fssp_partner_id = PARTNER.fid "
			+ "		<where> "
			+ "			<if test=\"form.sspPartnerId != null and form.sspPartnerId != '' \"> "
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
			+ "		ORDER BY SLOT.faudit_status ASC, SLOT.fid DESC "
			+ "	</script> ")
	List<SspAdvertSlotAuditDto> querySspAdvertSlotAuditList(@Param("form") SspAdvertSlotForm form);


	@Select("	SELECT 	fid AS id,"
			+ "			fname AS name,"
			+ "			fssp_partner_id AS sspPartnerId,"
			+ "			fapp_id AS appId,"
			+ "			ftype AS type,"
			+ "			fintegration_mode AS integrationMode,"
			+ "			fremarks AS remarks,"
			+ "			faudit_status AS auditStatus,"
			+ "			faudit_comments AS auditComments,"
			+ "			fctime AS ctime,"
			+ "			futime AS utime"
			+ "	FROM adx_ssp_ad_slot_audit "
			+ "	WHERE fid = #{id}")
	SspAdvertSlotAudit querySspAdvertSlotAudit(@Param("id") Integer id);
	

	@Select("	SELECT MAX(fid) "
			+ "	FROM adx_ssp_ad_slot_audit "
			+ "	WHERE fapp_id = #{appId}  ")
	Integer queryMaxSlotId(@Param("appId") Integer appId);
	
	
	@Insert("	INSERT INTO adx_ssp_ad_slot_audit ("
			+ "		fid,"
			+ "		fssp_partner_id,"
			+ "		fapp_id,"
			+ "		fname,"
			+ "		ftype,"
			+ "		fintegration_mode,"
			+ "		fremarks,"
			+ "		faudit_status,"
			+ "		faudit_comments,"
			+ "		fctime,"
			+ "		futime"
			+ "	) VALUES ("
			+ "		#{slotAudit.id},"
			+ "		#{slotAudit.sspPartnerId},"
			+ "		#{slotAudit.appId},"
			+ "		#{slotAudit.name},"
			+ "		#{slotAudit.type},"
			+ "		#{slotAudit.integrationMode},"
			+ "		#{slotAudit.remarks},"
			+ "		#{slotAudit.auditStatus},"
			+ "		#{slotAudit.auditComments},"
			+ "		#{slotAudit.ctime},"
			+ "		#{slotAudit.utime}"
			+ ")")
	int saveSspSlotAudit(@Param("slotAudit") SspAdvertSlotAudit slotAudit);

	@Update("	UPDATE adx_ssp_ad_slot_audit "
			+ "	SET fname = #{slotAudit.name}, "
			+ "		ftype = #{slotAudit.type}, "
			+ "		fintegration_mode = #{slotAudit.integrationMode}, "
			+ "		fremarks = #{slotAudit.remarks}, "
			+ "		futime = #{slotAudit.utime} "
			+ "	WHERE fid = #{slotAudit.id} "
			+ "")
	int updateSspSlotAudit(@Param("slotAudit") SspAdvertSlotAudit slotAudit);
	
	@Update("	UPDATE adx_ssp_ad_slot_audit "
			+ "	SET faudit_status = #{slotAudit.auditStatus}, "
			+ "		futime = #{slotAudit.utime} "
			+ "	WHERE fid = #{slotAudit.id} "
			+ "")
	int updateAuditStatus(@Param("slotAudit") SspAdvertSlotAudit updateSlotAudit);




}
