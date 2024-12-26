package com.juan.adx.channel.mapper.adx;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.juan.adx.model.dto.sspmanage.ChannelAppAuditDto;
import com.juan.adx.model.dto.sspmanage.ChannelAppOptionDto;
import com.juan.adx.model.entity.manage.SspAppAudit;
import com.juan.adx.model.form.sspmanage.ChannelAppForm;

@Mapper
public interface ChannelAppAuditMapper {
	

	@Select("	<script>"
			+ "		SELECT 	APP.fid AS id,"
			+ "				APP.fssp_partner_id AS sspPartnerId,"
			+ "				APP.fname AS name,"
			+ "				APP.fpgk_name AS packageName,"
			+ "				APP.fsystem_platform AS systemPlatform,"
			+ "				APP.findustry_id AS industryId,"
			+ "				APP.fapp_store_id AS appStoreId,"
			+ "				APP.fdownload_url AS downloadUrl,"
			+ "				APP.faudit_status AS auditStatus,"
			+ "				APP.faudit_comments AS auditComments,"
			+ "				APP.fctime AS ctime,"
			+ "				INDUSTRY.fname AS industryName,"
			+ "				STORE.fname AS appStoreName "
			+ "		FROM adx_ssp_app_audit AS APP LEFT JOIN adx_industry AS INDUSTRY ON APP.findustry_id = INDUSTRY.fid "
			+ "			LEFT JOIN adx_app_store AS STORE ON APP.fapp_store_id = STORE.fid "
			+ "		WHERE APP.fssp_partner_id = #{form.sspPartnerId} "
			+ "			<if test=\"form.appName != null and form.appName != '' \"> "
			+ "				AND APP.fname like concat('%', #{form.appName},'%')"
			+ "			</if> "
			+ "		ORDER BY APP.fid DESC "
			+ "	</script> ")
	List<ChannelAppAuditDto> querySspAppAuditList(@Param("form") ChannelAppForm form);
	

	@Select("	SELECT 	APP.fid AS id,"
			+ "			APP.fssp_partner_id AS sspPartnerId,"
			+ "			APP.fname AS name,"
			+ "			APP.fpgk_name AS packageName,"
			+ "			APP.fsystem_platform AS systemPlatform,"
			+ "			APP.findustry_id AS industryId,"
			+ "			APP.fapp_store_id AS appStoreId,"
			+ "			APP.fdownload_url AS downloadUrl,"
			+ "			APP.faudit_status AS auditStatus,"
			+ "			APP.faudit_comments AS auditComments,"
			+ "			APP.fctime AS ctime,"
			+ "			INDUSTRY.fname AS industryName,"
			+ "			STORE.fname AS appStoreName "
			+ "	FROM adx_ssp_app_audit AS APP LEFT JOIN adx_industry AS INDUSTRY ON APP.findustry_id = INDUSTRY.fid "
			+ "		LEFT JOIN adx_app_store AS STORE ON APP.fapp_store_id = STORE.fid "
			+ "	WHERE APP.fssp_partner_id = #{sspPartnerId} AND APP.fid = #{id} ")	
	ChannelAppAuditDto querySspAppAudit(@Param("sspPartnerId") Integer sspPartnerId, @Param("id") Integer id);
	
	@Select("	<script>"
			+ "		SELECT 	fid AS id,"
			+ "				fname AS name"
			+ "		FROM adx_ssp_app_audit "
			+ "		WHERE fssp_partner_id = #{sspPartnerId} "
			+ "			<if test=\"name != null and name != '' \"> "
			+ "				AND fname like concat('%', #{name}, '%')"
			+ "			</if> "
			+ "		ORDER BY fid DESC "
			+ "	</script>")
	List<ChannelAppOptionDto> querySspAppAuditOption(@Param("sspPartnerId") Integer sspPartnerId, @Param("name") String name);
	

	@Insert("	INSERT INTO adx_ssp_app_audit ("
			+ "		fssp_partner_id,"
			+ "		fname,"
			+ "		fpgk_name,"
			+ "		fsystem_platform,"
			+ "		findustry_id,"
			+ "		fapp_store_id,"
			+ "		fdownload_url,"
			+ "		faudit_status,"
			+ "		faudit_comments,"
			+ "		fctime,"
			+ "		futime"
			+ "	) VALUES ("
			+ "		#{appAudit.sspPartnerId},"
			+ "		#{appAudit.name},"
			+ "		#{appAudit.packageName},"
			+ "		#{appAudit.systemPlatform},"
			+ "		#{appAudit.industryId},"
			+ "		#{appAudit.appStoreId},"
			+ "		#{appAudit.downloadUrl},"
			+ "		#{appAudit.auditStatus},"
			+ "		#{appAudit.auditComments},"
			+ "		#{appAudit.ctime},"
			+ "		#{appAudit.utime}"
			+ ")")
	int saveSspAppAudit(@Param("appAudit") SspAppAudit appAudit);


	@Update("	UPDATE adx_ssp_app_audit "
			+ "	SET fname = #{appAudit.name},"
			+ "		fsystem_platform = #{appAudit.systemPlatform},"
			+ "		fdownload_url = #{appAudit.downloadUrl},"
			+ "		fapp_store_id = #{appAudit.appStoreId},"
			+ "		findustry_id = #{appAudit.industryId},"
			+ "		faudit_status = #{appAudit.auditStatus},"
			+ "		faudit_comments = #{appAudit.auditComments},"
			+ "		futime = #{appAudit.utime}"
			+ "	WHERE fssp_partner_id = #{appAudit.sspPartnerId} AND  fid = #{appAudit.id}")
	int updateSspAppAudit(@Param("appAudit") SspAppAudit appAudit);





}
