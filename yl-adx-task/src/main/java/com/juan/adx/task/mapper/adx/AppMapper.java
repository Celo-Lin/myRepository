package com.juan.adx.task.mapper.adx;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.juan.adx.model.entity.manage.SspApp;

@Mapper
public interface AppMapper {

	String SQL_COLUMN = "	fid AS id,"
			+ "				fssp_partner_id AS sspPartnerId,"
			+ "				fname AS name,"
			+ "				fpgk_name AS packageName,"
			+ "				fsystem_platform AS systemPlatform,"
			+ "				findustry_id AS industryId,"
			+ "				fapp_store_id AS appStoreId,"
			+ "				fdownload_url AS downloadUrl,"
			+ "				fstatus AS status,"
			+ "				fctime AS ctime,"
			+ "				futime AS utime";
	
	
	@Select("	SELECT " + SQL_COLUMN
			+ "	FROM adx_ssp_app "
			+ "	WHERE fid = #{id} ")
	SspApp queryAppById(@Param("id") Integer id);

}
