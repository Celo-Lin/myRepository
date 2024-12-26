package com.juan.adx.manage.mapper.adx;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.juan.adx.model.dto.manage.SspAppAuditDto;
import com.juan.adx.model.entity.manage.SspAppAudit;
import com.juan.adx.model.form.manage.SspAppForm;

@Mapper
public interface SspAppAuditMapper {

	String SQL_COLUMN = "	fid AS id,"
			+ "				fssp_partner_id AS sspPartnerId,"
			+ "				fname AS name,"
			+ "				fpgk_name AS packageName,"
			+ "				fsystem_platform AS systemPlatform,"
			+ "				findustry_id AS industryId,"
			+ "				fapp_store_id AS appStoreId,"
			+ "				fdownload_url AS downloadUrl,"
			+ "				faudit_status AS auditStatus,"
			+ "				faudit_comments AS auditComments,"
			+ "				fctime AS ctime,"
			+ "				futime AS utime";
	

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
			+ "				PARTNER.fname AS sspPartnerName,"
			+ "				INDUSTRY.fname AS industryName,"
			+ "				STORE.fname AS appStoreName "
			+ "		FROM adx_ssp_app_audit AS APP LEFT JOIN adx_ssp_partner AS PARTNER ON APP.fssp_partner_id = PARTNER.fid "
			+ "			LEFT JOIN adx_industry AS INDUSTRY ON APP.findustry_id = INDUSTRY.fid "
			+ "			LEFT JOIN adx_app_store AS STORE ON APP.fapp_store_id = STORE.fid "
			+ "		<where> "
			+ "			<if test=\"form.sspPartnerId != null and form.sspPartnerId != '' \"> "
			+ "				AND APP.fssp_partner_id = #{form.sspPartnerId} "
			+ "			</if> "
			+ "			<if test=\"form.appId != null and form.appId > 0 \"> "
			+ "				AND APP.fid = #{form.appId} "
			+ "			</if> "
			+ "			<if test=\"form.appName != null and form.appName != '' \"> "
			+ "				AND APP.fname like concat(#{form.appName},'%')"
			+ "			</if> "
			+ "		</where> "
			+ "		ORDER BY APP.faudit_status ASC, APP.fid DESC "
			+ "	</script> ")
	List<SspAppAuditDto> querySspAppAuditList(@Param("form") SspAppForm form);

	@Select("	SELECT COUNT(1) "
			+ "	FROM adx_ssp_app_audit "
			+ "	WHERE fssp_partner_id = #{sspPartnerId} ")
	int countAppAuditByPartnerId(@Param("sspPartnerId") Integer sspPartnerId);
	

	@Select("	SELECT " + SQL_COLUMN
			+ "	FROM adx_ssp_app_audit "
			+ "	WHERE fid = #{id}")
	SspAppAudit querySspAppAudit(@Param("id") Integer id);

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
	@Options(useGeneratedKeys = true, keyProperty = "appAudit.id", keyColumn = "fid")
	int saveSspAppAudit(@Param("appAudit") SspAppAudit appAudit);

	@Update("	UPDATE adx_ssp_app_audit "
			+ "	SET fname = #{appAudit.name},"
			+ "		fsystem_platform = #{appAudit.systemPlatform},"
			+ "		fdownload_url = #{appAudit.downloadUrl},"
			+ "		fapp_store_id = #{appAudit.appStoreId},"
			+ "		findustry_id = #{appAudit.industryId},"
			+ "		futime = #{appAudit.utime}"
			+ "	WHERE fid = #{appAudit.id}")
	int updateSspAppAudit(@Param("appAudit") SspAppAudit appAudit);
	
	@Update("	UPDATE adx_ssp_app_audit "
			+ "	SET faudit_status = #{appAudit.auditStatus},"
			+ "		futime = #{appAudit.utime}"
			+ "	WHERE fid = #{appAudit.id}")
	int updateSspAppStatus(@Param("appAudit") SspAppAudit appAudit);
	
	@Delete("DELETE FROM adx_ssp_app_audit WHERE fid = #{id}")
	int deleteSspAppAudit(@Param("id") Integer id);




}
