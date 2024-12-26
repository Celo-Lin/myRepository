package com.juan.adx.task.mapper.adx;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.juan.adx.model.entity.manage.DspBudget;

@Mapper
public interface BudgetMapper {

	String SQL_COLUMN = "	fid AS id,"
			+ "				fdsp_partner_id AS dspPartnerId,"
			+ "				fname AS name,"
			+ "				ftitle AS title,"
			+ "				fcooperation_mode AS cooperationMode,"
			+ "				fpicture_url AS pictureUrl,"
			+ "				fvideo_url AS videoUrl,"
			+ "				fdeeplink AS deeplink,"
			+ "				fh5link AS h5link,"
			+ "				fdownload_url AS downloadUrl,"
			+ "				fdevice_max_requests AS deviceMaxRequests,"
			+ "				ftype AS type,"
			+ "				fstatus AS status,"
			+ "				fctime AS ctime,"
			+ "				futime AS utime";
	
	@Select("	SELECT " + SQL_COLUMN
			+ "	FROM adx_dsp_budget "
			+ "	WHERE fid = #{id} AND fstatus = #{status} ")
	DspBudget queryBudgetById(@Param("id") Integer budgetId, @Param("status") Integer status);
	
	@Select("	SELECT " + SQL_COLUMN
			+ "	FROM adx_dsp_budget "
			+ "	WHERE fid > #{id} AND fstatus = #{status} "
			+ "	ORDER BY fid ASC "
			+ "	LIMIT ${size} ")
	List<DspBudget> queryBudgetList(@Param("id") Integer budgetId, @Param("status") Integer status, @Param("size") Integer size);

}
