package com.juan.adx.manage.mapper.adx;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.juan.adx.model.dto.manage.SlotBudgetDto;
import com.juan.adx.model.entity.manage.SlotBudget;

@Mapper
public interface SlotBudgetMapper {

	
	String SQL_COLUMN = "	fid AS id,"
			+ "				fad_slot_id AS slotId,"
			+ "				fbudget_id AS budgetId,"
			+ "				fdsp_app_id AS dspAppId,"
			+ "				fdsp_slot_id AS dspSlotId,"
			+ "				flimit_type AS limitType,"
			+ "				fmax_requests AS maxRequests,"
			+ "				fpkgname AS packageName,"
			+ "				fssp_floor_price AS sspFloorPrice,"
			+ "				fdsp_floor_price AS dspFloorPrice,"
			+ "				fssp_premium_rate AS sspPremiumRate,"
			+ "				fdsp_floating_rate AS dspFloatingRate,"
			+ "				fhas_rta AS hasRta,"
			+ "				frta_priority_value AS rtaPriorityValue,"
			+ "				frta_ssp_floor_price AS rtaSspFloorPrice,"
			+ "				fctime AS ctime,"
			+ "				futime AS utime";
	
	@Select(" 	SELECT COUNT(1) "
			+ "	FROM adx_ad_slot_budget "
			+ "	WHERE fbudget_id = #{budgetId} ")
	int countMappingByBudgetId(@Param("budgetId") Integer budgetId);

	@Select(" 	SELECT COUNT(1) "
			+ "	FROM adx_ad_slot_budget "
			+ "	WHERE fad_slot_id = #{slotId} ")
	int countMappingBySlotId(@Param("slotId") Integer slotId);
	
	@Select(" 	SELECT COUNT(1) "
			+ "	FROM adx_ad_slot_budget "
			+ "	WHERE fad_slot_id = #{slotId}"
			+ "		AND fbudget_id = #{budgetId} ")
	int countMappingBySlotIdAndBudgetId(@Param("slotId") Integer slotId, @Param("budgetId") Integer budgetId);

	@Select("	SELECT 	SLOTBUDGET.fid AS id,"
			+ "			SLOTBUDGET.fad_slot_id AS slotId,"
			+ "			SLOTBUDGET.fbudget_id AS budgetId,"
			+ "			BUDGET.fname AS budgetName,"
			+ "			SLOTBUDGET.fdsp_app_id AS dspAppId,"
			+ "			SLOTBUDGET.fdsp_slot_id AS dspSlotId,"
			+ "			SLOTBUDGET.flimit_type AS limitType,"
			+ "			SLOTBUDGET.fmax_requests AS maxRequests,"
			+ "			SLOTBUDGET.fpkgname AS packageName,"
			+ "			SLOTBUDGET.fssp_floor_price AS sspFloorPrice,"
			+ "			SLOTBUDGET.fdsp_floor_price AS dspFloorPrice,"
			+ "			SLOTBUDGET.fssp_premium_rate AS sspPremiumRate,"
			+ "			SLOTBUDGET.fdsp_floating_rate AS dspFloatingRate,"
			+ "			SLOTBUDGET.fhas_rta AS hasRta,"
			+ "			SLOTBUDGET.frta_priority_value AS rtaPriorityValue,"
			+ "			SLOTBUDGET.frta_ssp_floor_price AS rtaSspFloorPrice,"
			+ "			SLOTBUDGET.fctime AS ctime,"
			+ "			SLOTBUDGET.futime AS utime,"
			+ "			PARTNER.fid AS dspPartnerId,"
			+ "			PARTNER.fname AS dspPartnerName"
			+ "	FROM adx_ad_slot_budget AS SLOTBUDGET LEFT JOIN adx_dsp_budget BUDGET ON SLOTBUDGET.fbudget_id = BUDGET.fid "
			+ "		LEFT JOIN adx_dsp_partner AS PARTNER ON BUDGET.fdsp_partner_id = PARTNER.fid "
			+ "	WHERE SLOTBUDGET.fad_slot_id = #{slotId} ")
	List<SlotBudgetDto> querySlotBudgetList(@Param("slotId") Integer slotId);

	@Select("	SELECT 	SLOTBUDGET.fid AS id,"
			+ "			SLOTBUDGET.fad_slot_id AS slotId,"
			+ "			SLOTBUDGET.fbudget_id AS budgetId,"
			+ "			BUDGET.fname AS budgetName,"
			+ "			SLOTBUDGET.fdsp_app_id AS dspAppId,"
			+ "			SLOTBUDGET.fdsp_slot_id AS dspSlotId,"
			+ "			SLOTBUDGET.flimit_type AS limitType,"
			+ "			SLOTBUDGET.fmax_requests AS maxRequests,"
			+ "			SLOTBUDGET.fpkgname AS packageName,"
			+ "			SLOTBUDGET.fssp_floor_price AS sspFloorPrice,"
			+ "			SLOTBUDGET.fdsp_floor_price AS dspFloorPrice,"
			+ "			SLOTBUDGET.fssp_premium_rate AS sspPremiumRate,"
			+ "			SLOTBUDGET.fdsp_floating_rate AS dspFloatingRate,"
			+ "			SLOTBUDGET.fhas_rta AS hasRta,"
			+ "			SLOTBUDGET.frta_priority_value AS rtaPriorityValue,"
			+ "			SLOTBUDGET.frta_ssp_floor_price AS rtaSspFloorPrice,"
			+ "			SLOTBUDGET.fctime AS ctime,"
			+ "			SLOTBUDGET.futime AS utime,"
			+ "			PARTNER.fid AS dspPartnerId,"
			+ "			PARTNER.fname AS dspPartnerName"
			+ "	FROM adx_ad_slot_budget AS SLOTBUDGET LEFT JOIN adx_dsp_budget BUDGET ON SLOTBUDGET.fbudget_id = BUDGET.fid "
			+ "		LEFT JOIN adx_dsp_partner AS PARTNER ON BUDGET.fdsp_partner_id = PARTNER.fid "
			+ "	WHERE SLOTBUDGET.fid = #{id} ")
	SlotBudgetDto querySlotBudget(@Param("id") Integer id);
	
	@Select("	SELECT " + SQL_COLUMN 
			+ "	FROM adx_ad_slot_budget"
			+ "	WHERE fad_slot_id = #{slotId} AND fbudget_id = #{budgetId} ")
	SlotBudget querySlotBudgetByBudgetIdAndSlotId(@Param("slotId") Integer slotId, @Param("budgetId") Integer budgetId);

	@Insert("	INSERT INTO adx_ad_slot_budget ("
			+ "		fad_slot_id,"
			+ "		fbudget_id,"
			+ "		fdsp_app_id,"
			+ "		fdsp_slot_id,"
			+ "		flimit_type,"
			+ "		fmax_requests,"
			+ "		fpkgname,"
			+ "		fssp_floor_price,"
			+ "		fdsp_floor_price,"
			+ "		fssp_premium_rate,"
			+ "		fdsp_floating_rate,"
			+ "		fhas_rta,"
			+ "		frta_priority_value,"
			+ "		frta_ssp_floor_price,"
			+ "		fctime,"
			+ "		futime"
			+ "	) VALUES ("
			+ "		#{slotBudget.slotId},"
			+ "		#{slotBudget.budgetId},"
			+ "		#{slotBudget.dspAppId},"
			+ "		#{slotBudget.dspSlotId},"
			+ "		#{slotBudget.limitType},"
			+ "		#{slotBudget.maxRequests},"
			+ "		#{slotBudget.packageName},"
			+ "		#{slotBudget.sspFloorPrice},"
			+ "		#{slotBudget.dspFloorPrice},"
			+ "		#{slotBudget.sspPremiumRate},"
			+ "		#{slotBudget.dspFloatingRate},"
			+ "		#{slotBudget.hasRta},"
			+ "		#{slotBudget.rtaPriorityValue},"
			+ "		#{slotBudget.rtaSspFloorPrice},"
			+ "		#{slotBudget.ctime},"
			+ "		#{slotBudget.utime}"
			+ "	)")
	int saveSlotBudget(@Param("slotBudget") SlotBudget slotBudget);

	@Update("	UPDATE adx_ad_slot_budget "
			+ "	SET fdsp_app_id = #{slotBudget.dspAppId},"
			+ "		fdsp_slot_id = #{slotBudget.dspSlotId},"
			+ "		flimit_type = #{slotBudget.limitType},"
			+ "		fmax_requests = #{slotBudget.maxRequests},"
			+ "		fpkgname = #{slotBudget.packageName},"
			+ "		fssp_floor_price = #{slotBudget.sspFloorPrice},"
			+ "		fdsp_floor_price = #{slotBudget.dspFloorPrice},"
			+ "		fssp_premium_rate = #{slotBudget.sspPremiumRate},"
			+ "		fdsp_floating_rate = #{slotBudget.dspFloatingRate},"
			+ "		fhas_rta = #{slotBudget.hasRta},"
			+ "		frta_priority_value = #{slotBudget.rtaPriorityValue},"
			+ "		frta_ssp_floor_price = #{slotBudget.rtaSspFloorPrice},"
			+ "		futime = #{slotBudget.utime}"
			+ "	WHERE fid = #{slotBudget.id}")
	int updateSlotBudget(@Param("slotBudget") SlotBudget slotBudget);

	@Delete("DELETE FROM adx_ad_slot_budget WHERE fid = #{id}")
	int deleteSlotBudget(@Param("id") Integer id);


}
