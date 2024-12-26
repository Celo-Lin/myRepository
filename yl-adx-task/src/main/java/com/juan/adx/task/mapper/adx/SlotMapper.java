package com.juan.adx.task.mapper.adx;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.juan.adx.model.entity.manage.SspAdvertSlot;

@Mapper
public interface SlotMapper {
	

	String SQL_COLUMN = " 	fid AS id, "
			+ "				fssp_partner_id AS sspPartnerId, "
			+ "				fapp_id AS appId, "
			+ "				fname AS name, "
			+ "				ftype AS type, "
			+ "				fintegration_mode AS integrationMode, "
			+ "				fcooperation_mode AS cooperationMode, "
			+ "				fssp_bid_price AS sspBidPrice, "
			+ "				fstatus AS status, "
			+ "				fremarks AS remarks, "
			+ "				fctime AS ctime, "
			+ "				futime AS utime ";
	

	@Select("	SELECT " + SQL_COLUMN
			+ "	FROM adx_ssp_ad_slot "
			+ "	ORDER BY fid DESC ")
	List<SspAdvertSlot> queryAllSlots();

	@Select("	SELECT " + SQL_COLUMN
			+ "	FROM adx_ssp_ad_slot "
			+ "	WHERE fid = #{slotId} ")
	SspAdvertSlot querySlotById(@Param("slotId") Integer slotId);

}
