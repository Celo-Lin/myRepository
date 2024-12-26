package com.juan.adx.task.mapper.adx;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.juan.adx.model.entity.manage.SlotBudget;

@Mapper
public interface SlotBudgetMapper {

	String SQL_COLUMN = "	fid AS id, "
			+ "				fad_slot_id AS slotId, "
			+ "				fbudget_id AS budgetId, "
			+ "				fdsp_app_id AS dspAppId, "
			+ "				fdsp_slot_id AS dspSlotId, "
			+ "				flimit_type AS limitType, "
			+ "				fmax_requests AS maxRequests, "
			+ "				fpkgname AS packageName, "
			+ "				fssp_floor_price AS sspFloorPrice, "
			+ "				fdsp_floor_price AS dspFloorPrice, "
			+ "				fssp_premium_rate AS sspPremiumRate, "
			+ "				fdsp_floating_rate AS dspFloatingRate, "
			+ "				fhas_rta AS hasRta, "
			+ "				frta_priority_value AS rtaPriorityValue, "
			+ "				frta_ssp_floor_price AS rtaSspFloorPrice";
	
	
	@Select("	SELECT " + SQL_COLUMN 
			+ "	FROM adx_ad_slot_budget "
			+ "	WHERE fad_slot_id = #{slotId} ")
	List<SlotBudget> queryAdSlotBudgetListBySlotId(@Param("slotId") Integer slotId);

	@Select("	SELECT " + SQL_COLUMN 
			+ "	FROM adx_ad_slot_budget "
			+ "	WHERE fad_slot_id = #{slotId} "
			+ "		AND fbudget_id = #{budgetId} ")
	SlotBudget queryAdSlotBudgetBySlotIdWithBudgetId(@Param("slotId") Integer slotId, @Param("budgetId") Integer budgetId);

}
