package com.juan.adx.manage.mapper.adx;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.juan.adx.model.entity.manage.AppStore;
import com.juan.adx.model.entity.manage.Industry;

@Mapper
public interface DictMapper {

	@Select(" 	SELECT 	fid AS id, "
			+ "			fname AS name "
			+ "	FROM adx_app_store ")
	List<AppStore> allAppStore();
	
	@Select(" 	SELECT 	fid AS id, "
			+ "			fname AS name "
			+ "	FROM adx_industry ")
	List<Industry> allIndustry();

}
