package com.juan.adx.task.mapper.adx;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.juan.adx.model.entity.manage.DspPartnerConfig;

@Mapper
public interface DspPartnerConfigMapper {
	
	String SQL_COLUMN = "	fdsp_partner_id AS dspPartnerId,"
			+ "				fpinyin_name AS pinyinName,"
			+ "				fapi_url AS apiUrl,"
			+ "				fprice_encrypt_key AS priceEncryptKey,"
			+ "				frta_api_sign_key AS rtaApiSignKey,"
			+ "				frta_api_url AS rtaApiUrl";

	
	
	@Select("	SELECT " + SQL_COLUMN
			+ "	FROM adx_dsp_partner_config "
			+ "	WHERE fdsp_partner_id = #{dspPartnerId} ")
	DspPartnerConfig queryDspPartnerConfig(@Param("dspPartnerId") Integer dspPartnerId);

}
