package com.juan.adx.api.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.juan.adx.model.entity.SysParameter;

@Mapper
public interface SysParameterMapper {

	
	@Select("	SELECT 	fkey AS `key`,"
			+ "			fvalue AS `value`,"
			+ "			fdescription AS description "
			+ "	FROM adx_sys_parameter_api ")
	List<SysParameter> queryAllSysParameter();
}
