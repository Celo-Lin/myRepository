package com.juan.adx.task.mapper.adx;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.juan.adx.model.entity.manage.DspPartner;

@Mapper
public interface DspPartnerMapper {
	
	String SQL_COLUMN = "	fid AS id,"
			+ "				fname AS name,"
			+ "				fcompany AS company,"
			+ "				fcontact_name AS contactName,"
			+ "				fphone AS phone,"
			+ "				fstatus AS status,"
			+ "				fctime AS ctime,"
			+ "				futime AS utime";

	
	
	@Select("	SELECT " + SQL_COLUMN
			+ "	FROM adx_dsp_partner "
			+ "	WHERE fid = #{dspPartnerId} ")
	DspPartner queryDspPartner(@Param("dspPartnerId") Integer dspPartnerId);

}
