package com.juan.adx.manage.mapper.adx;

import com.juan.adx.model.dto.manage.DspStrategyDto;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 2024年12月10日17:45:55
 * Auth: Kevin.zhao
 * Desc: dsp预算方测试配置表实体
 */
@Mapper
public interface DspBudgetStrategyMapper {

    String SQL_COLUMN = "	id,"
			+ "				dsp_partner_fid AS dspPartnerId,"
			+ "				code  ,"
			+ "				parent_code AS parentCode,"
			+ "				note ,"
			+ "				stg_type AS stgType,"
			+ "				fixed_value AS fixedValue,"
			+ "				range_begin_value AS rangeBeginValue,"
			+ "				range_end_value AS rangeEndValue,"
			+ "				value_unit AS valueUnit,"
			+ "				status ,"
			+ "				create_by AS createBy,"
			+ "				create_time AS createTime,"
			+ "				update_by AS updateBy,"
			+ "				update_time AS updateTime";

    @Select("	<script>"
            + "		SELECT " + SQL_COLUMN + " FROM adx_dsp_strategy AS BUDGET  where status = 1"
            + "	</script> ")
    List<DspStrategyDto> selectAllUsefulStrategyList();


}
