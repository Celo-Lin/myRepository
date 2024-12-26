package com.juan.adx.manage.mapper.adx;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.juan.adx.model.entity.SysParameter;


@Mapper
public interface SysParameterMapper {

	String SQL_COLUMN = " fkey AS 'key', fvalue AS 'value', fdescription AS description ";
	
	@Select("	SELECT " + SQL_COLUMN
			+ "	FROM adx_sys_parameter_manage ")
	List<SysParameter> allSysParameters();
}
