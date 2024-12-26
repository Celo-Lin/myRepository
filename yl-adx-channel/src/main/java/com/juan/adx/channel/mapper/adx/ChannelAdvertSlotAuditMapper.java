package com.juan.adx.channel.mapper.adx;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.juan.adx.model.dto.sspmanage.ChannelAdvertSlotAuditDto;
import com.juan.adx.model.entity.manage.SspAdvertSlotAudit;
import com.juan.adx.model.form.sspmanage.ChannelAdvertSlotForm;

@Mapper
public interface ChannelAdvertSlotAuditMapper {
	
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
			+ "				APP.fname AS appName "
			+ "		FROM adx_ssp_ad_slot_audit AS SLOT LEFT JOIN adx_ssp_app_audit AS APP ON SLOT.fapp_id = APP.fid "
			+ "		WHERE SLOT.fssp_partner_id = #{form.sspPartnerId} "
			+ "			<if test=\"form.slotName != null and form.slotName != '' \"> "
			+ "				APP SLOT.fname like concat('%', #{form.slotName},'%')"
			+ "			</if> "
			+ "			<if test=\"form.appId != null and form.appId != '' \"> "
			+ "				AND APP.fid = #{form.appId}"
			+ "			</if> "
			+ "			<if test=\"form.appName != null and form.appName != '' \"> "
			+ "				AND APP.fname like concat('%', #{form.appName},'%')"
			+ "			</if> "
			+ "		ORDER BY SLOT.fid DESC "
			+ "	</script> ")
	List<ChannelAdvertSlotAuditDto> querySspAdvertSlotAuditList(@Param("form") ChannelAdvertSlotForm form);
	
	@Select("	SELECT 	SLOT.fid AS id,"
			+ "			SLOT.fname AS name,"
			+ "			SLOT.fssp_partner_id AS sspPartnerId,"
			+ "			SLOT.fapp_id AS appId,"
			+ "			SLOT.ftype AS type,"
			+ "			SLOT.fintegration_mode AS integrationMode,"
			+ "			SLOT.fremarks AS remarks,"
			+ "			SLOT.faudit_status AS auditStatus,"
			+ "			SLOT.faudit_comments AS auditComments,"
			+ "			SLOT.fctime AS ctime,"
			+ "			SLOT.futime AS utime,"
			+ "			APP.fname AS appName "
			+ "	FROM adx_ssp_ad_slot_audit AS SLOT LEFT JOIN adx_ssp_app AS APP ON SLOT.fapp_id = APP.fid "
			+ "	WHERE SLOT.fssp_partner_id = #{sspPartnerId} AND SLOT.fid = #{id}  ")
	ChannelAdvertSlotAuditDto querySspAdvertSlotAudit(@Param("sspPartnerId") Integer sspPartnerId, @Param("id") Integer id);
	
	@Select("	SELECT MAX(fid) "
			+ "	FROM adx_ssp_ad_slot_audit "
			+ "	WHERE fssp_partner_id = #{sspPartnerId} AND fapp_id = #{appId}  ")
	Integer queryMaxSlotId(@Param("sspPartnerId") Integer sspPartnerId, @Param("appId") Integer appId);
	
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
	int saveSspAdvertSlotAudit(@Param("slotAudit") SspAdvertSlotAudit slotAudit);

	@Update("	UPDATE adx_ssp_ad_slot_audit "
			+ "	SET fname = #{slotAudit.name}, "
			+ "		ftype = #{slotAudit.type}, "
			+ "		fintegration_mode = #{slotAudit.integrationMode}, "
			+ "		fremarks = #{slotAudit.remarks}, "
			+ "		faudit_status = #{slotAudit.auditStatus}, "
			+ "		faudit_comments = #{slotAudit.auditComments}, "
			+ "		futime = #{slotAudit.utime} "
			+ "	WHERE fssp_partner_id = #{slotAudit.sspPartnerId} AND fid = #{slotAudit.id} ")
	int updateSspSlotAudit(@Param("slotAudit") SspAdvertSlotAudit slotAudit);


	


}
