package com.juan.adx.task.mapper.adx;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.juan.adx.model.entity.manage.SspPartner;

@Mapper
public interface SspPartnerMapper {
	
	String SQL_COLUMN = "	fid AS id,"
			+ "				fname AS name,"
			+ "				fcompany AS company,"
			+ "				fcontact_name AS contactName,"
			+ "				fphone AS phone,"
			+ "				fstatus AS status,"
			+ "				fctime AS ctime,"
			+ "				futime AS utime";

	
	
	@Select("	SELECT " + SQL_COLUMN
			+ "	FROM adx_ssp_partner "
			+ "	WHERE fid = #{sspPartnerId} ")
	SspPartner querySspPartner(@Param("sspPartnerId") Integer sspPartnerId);
	
	
	@Select("	SELECT " + SQL_COLUMN
			+ "	FROM adx_ssp_partner ")
	List<SspPartner> querySspPartnerAll();

}
