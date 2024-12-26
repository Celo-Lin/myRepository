package com.juan.adx.manage.dao.adx;

import com.juan.adx.manage.mapper.adx.DspBudgetStrategyMapper;
import com.juan.adx.model.dto.manage.DspStrategyDto;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
/**
 * 2024年12月10日17:45:55
 * Auth: Kevin.zhao
 * Desc: dsp预算方测试配置表实体
 */
@Repository
public class DspBudgetStrategyDao {

	@Resource
	private DspBudgetStrategyMapper strategyMapper ;

	public List<DspStrategyDto> queryDspBudgetOption( ) {
		return this.strategyMapper.selectAllUsefulStrategyList();
	}
}
