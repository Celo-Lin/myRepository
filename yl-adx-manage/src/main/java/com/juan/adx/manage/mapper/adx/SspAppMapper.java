package com.juan.adx.manage.mapper.adx;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.juan.adx.model.dto.manage.SspAppDto;
import com.juan.adx.model.dto.manage.SspAppOptionDto;
import com.juan.adx.model.entity.manage.SspApp;
import com.juan.adx.model.form.manage.SspAppForm;

@Mapper
public interface SspAppMapper {
	

	@Select("	<script>"
			+ "		SELECT 	APP.fid AS id,"
			+ "				APP.fssp_partner_id AS sspPartnerId,"
			+ "				APP.fname AS name,"
			+ "				APP.fpgk_name AS packageName,"
			+ "				APP.fsystem_platform AS systemPlatform,"
			+ "				APP.findustry_id AS industryId,"
			+ "				APP.fapp_store_id AS appStoreId,"
			+ "				APP.fdownload_url AS downloadUrl,"
			+ "				APP.fstatus AS status,"
			+ "				APP.fctime AS ctime,"
			+ "				APP.futime AS utime,"
			+ "				PARTNER.fname AS sspPartnerName,"
			+ "				INDUSTRY.fname AS industryName,"
			+ "				STORE.fname AS appStoreName "
			+ "		FROM adx_ssp_app AS APP LEFT JOIN adx_ssp_partner AS PARTNER ON APP.fssp_partner_id = PARTNER.fid "
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
			+ "		ORDER BY APP.fid DESC "
			+ "	</script> ")
	List<SspAppDto> querySspAppList(@Param("form") SspAppForm form);
	

	@Select("	SELECT 	APP.fid AS id,"
			+ "			APP.fssp_partner_id AS sspPartnerId,"
			+ "			APP.fname AS name,"
			+ "			APP.fpgk_name AS packageName,"
			+ "			APP.fsystem_platform AS systemPlatform,"
			+ "			APP.findustry_id AS industryId,"
			+ "			APP.fapp_store_id AS appStoreId,"
			+ "			APP.fdownload_url AS downloadUrl,"
			+ "			APP.fstatus AS status,"
			+ "			APP.fctime AS ctime,"
			+ "			PARTNER.fname AS sspPartnerName,"
			+ "			INDUSTRY.fname AS industryName,"
			+ "			STORE.fname AS appStoreName "
			+ "	FROM adx_ssp_app AS APP LEFT JOIN adx_ssp_partner AS PARTNER ON APP.fssp_partner_id = PARTNER.fid "
			+ "		LEFT JOIN adx_industry AS INDUSTRY ON APP.findustry_id = INDUSTRY.fid "
			+ "		LEFT JOIN adx_app_store AS STORE ON APP.fapp_store_id = STORE.fid"
			+ "	WHERE APP.fid = #{id} ")
	SspAppDto querySspApp(@Param("id") Integer id);
	
	@Select("	<script>"
			+ "		SELECT 	fid AS id,"
			+ "				fname AS name"
			+ "		FROM adx_ssp_app "
			+ "		<where> "
			+ "			<if test=\"name != null and name != '' \"> "
			+ "				AND fname like concat('%', #{name}, '%')"
			+ "			</if> "
			+ "		</where> "
			+ "		ORDER BY fid DESC "
			+ "	</script>")
	List<SspAppOptionDto> querySspAppOption(@Param("name") String name);
	
	@Select("	SELECT 	fid AS id,"
			+ "			fname AS name"
			+ "	FROM adx_ssp_app "
			+ "	WHERE fssp_partner_id = #{sspPartnerId} "
			+ "	ORDER BY fid DESC ")
	List<SspAppOptionDto> querySspAppSimpleBySspPartnerId(@Param("sspPartnerId") Integer sspPartnerId);

	@Insert("	INSERT INTO adx_ssp_app ("
			+ "		fid,"
			+ "		fssp_partner_id,"
			+ "		fname,"
			+ "		fpgk_name,"
			+ "		fsystem_platform,"
			+ "		findustry_id,"
			+ "		fapp_store_id,"
			+ "		fdownload_url,"
			+ "		fstatus,"
			+ "		fctime,"
			+ "		futime"
			+ "	) VALUES ("
			+ "		#{app.id},"
			+ "		#{app.sspPartnerId},"
			+ "		#{app.name},"
			+ "		#{app.packageName},"
			+ "		#{app.systemPlatform},"
			+ "		#{app.industryId},"
			+ "		#{app.appStoreId},"
			+ "		#{app.downloadUrl},"
			+ "		#{app.status},"
			+ "		#{app.ctime},"
			+ "		#{app.utime}"
			+ ")")
	int saveSspApp(@Param("app") SspApp app);


	@Update("	UPDATE adx_ssp_app "
			+ "	SET fname = #{app.name},"
			+ "		fsystem_platform = #{app.systemPlatform},"
			+ "		fdownload_url = #{app.downloadUrl},"
			+ "		fapp_store_id = #{app.appStoreId},"
			+ "		findustry_id = #{app.industryId},"
			+ "		futime = #{app.utime}"
			+ "	WHERE fid = #{app.id}")
	int updateSspApp(@Param("app") SspApp sspApp);
	
	@Update("	UPDATE adx_ssp_app "
			+ "	SET fname = #{app.name},"
			+ "		fsystem_platform = #{app.systemPlatform},"
			+ "		fdownload_url = #{app.downloadUrl},"
			+ "		fapp_store_id = #{app.appStoreId},"
			+ "		findustry_id = #{app.industryId},"
			+ "		futime = #{app.utime}"
			+ "	WHERE fid = #{app.id}")
	int syncChannelUpdateToSspApp(@Param("app") SspApp sspApp);

	@Update("	UPDATE adx_ssp_app "
			+ "	SET fstatus = #{status}"
			+ "	WHERE fid = #{id}")
	int updateSspAppStatus(@Param("id") Integer id, @Param("status") Integer status);

	@Delete("DELETE FROM adx_ssp_app WHERE fid = #{id}")
	int deleteSspApp(@Param("id") Integer id);





}
