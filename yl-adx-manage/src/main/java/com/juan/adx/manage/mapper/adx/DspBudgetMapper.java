package com.juan.adx.manage.mapper.adx;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.juan.adx.model.dto.manage.DspBudgetDto;
import com.juan.adx.model.dto.manage.DspBudgetOptionDto;
import com.juan.adx.model.entity.manage.DspBudget;
import com.juan.adx.model.form.manage.DspBudgetForm;

@Mapper
public interface DspBudgetMapper {
	
	String SQL_COLUMN = "	fid AS id,"
			+ "				fdsp_partner_id AS dspPartnerId,"
			+ "				fname AS name,"
			+ "				ftitle AS title,"
			+ "				fcooperation_mode AS cooperationMode,"
			+ "				fpicture_url AS pictureUrl,"
			+ "				fdeeplink AS deeplink,"
			+ "				fh5link AS h5link,"
			+ "				fdownload_url AS downloadUrl,"
			+ "				fdevice_max_requests AS deviceMaxRequests,"
			+ "				ftype AS type,"
			+ "				fstatus AS status,"
			+ "				fctime AS ctime,"
			+ "				futime AS utime";
	

	@Select("	SELECT COUNT(1) "
			+ "	FROM adx_dsp_budget "
			+ "	WHERE fdsp_partner_id = #{dspPartnerId} ")
	int countBudgetByPartnerId(@Param("dspPartnerId") Integer dspPartnerId);

	@Select("	<script>"
			+ "		SELECT 	BUDGET.fid AS id,"
			+ "				BUDGET.fdsp_partner_id AS dspPartnerId,"
			+ "				BUDGET.fname AS name,"
			+ "				BUDGET.ftitle AS title,"
			+ "				BUDGET.fcooperation_mode AS cooperationMode,"
			+ "				BUDGET.fpicture_url AS pictureUrl,"
			+ "				BUDGET.fvideo_url AS videoUrl,"
			+ "				BUDGET.fdeeplink AS deeplink,"
			+ "				BUDGET.fh5link AS h5link,"
			+ "				BUDGET.fdownload_url AS downloadUrl,"
			+ "				BUDGET.fdevice_max_requests AS deviceMaxRequests,"
			+ "				BUDGET.ftype AS type,"
			+ "				BUDGET.fstatus AS status,"
			+ "				BUDGET.fctime AS ctime,"
			+ "				BUDGET.futime AS utime,"
			+ "				PARTNER.fname AS dspPartnerName"
			+ "		FROM adx_dsp_budget AS BUDGET LEFT JOIN adx_dsp_partner AS PARTNER ON BUDGET.fdsp_partner_id = PARTNER.fid "
			+ "		<where> "
			+ "			<if test=\"form.dspPartnerId != null and form.dspPartnerId > 0 \"> "
			+ "				AND BUDGET.fdsp_partner_id = #{form.dspPartnerId} "
			+ "			</if> "
			+ "			<if test=\"form.advertType != null and form.advertType > 0 \"> "
			+ "				AND BUDGET.ftype = #{form.advertType} "
			+ "			</if> "
			+ "			<if test=\"form.cooperationMode != null and form.cooperationMode > 0 \"> "
			+ "				AND BUDGET.fcooperation_mode = #{form.cooperationMode} "
			+ "			</if> "
			+ "			<if test=\"form.name != null and form.name != '' \"> "
			+ "				AND BUDGET.fname like concat('%', #{form.name},'%')"
			+ "			</if> "
			+ "		</where> "
			+ "		ORDER BY BUDGET.fid DESC "
			+ "	</script> ")
	List<DspBudgetDto> queryDspBudgeList(@Param("form") DspBudgetForm form);
	
	@Select("	SELECT 	BUDGET.fid AS id,"
			+ "			BUDGET.fdsp_partner_id AS dspPartnerId,"
			+ "			BUDGET.fname AS name,"
			+ "			BUDGET.ftitle AS title,"
			+ "			BUDGET.fcooperation_mode AS cooperationMode,"
			+ "			BUDGET.fpicture_url AS pictureUrl,"
			+ "			BUDGET.fvideo_url AS videoUrl,"
			+ "			BUDGET.fdeeplink AS deeplink,"
			+ "			BUDGET.fh5link AS h5link,"
			+ "			BUDGET.fdownload_url AS downloadUrl,"
			+ "			BUDGET.fdevice_max_requests AS deviceMaxRequests,"
			+ "			BUDGET.ftype AS type,"
			+ "			BUDGET.fstatus AS status,"
			+ "			BUDGET.fctime AS ctime,"
			+ "			BUDGET.futime AS utime,"
			+ "			PARTNER.fname AS dspPartnerName"
			+ "	FROM adx_dsp_budget AS BUDGET LEFT JOIN adx_dsp_partner AS PARTNER ON BUDGET.fdsp_partner_id = PARTNER.fid "
			+ "	WHERE BUDGET.fid = #{id} ")
	DspBudgetDto queryDspBudget(@Param("id") Integer id);
	
	@Select("	SELECT 	fid AS id,"
			+ "			fname AS name "
			+ "	FROM adx_dsp_budget"
			+ "	WHERE fdsp_partner_id = #{dspPartnerId} ")
	List<DspBudgetOptionDto> queryDspBudgetOption(@Param("dspPartnerId")  Integer dspPartnerId);

	@Insert("	INSERT INTO adx_dsp_budget ("
			+ "		fdsp_partner_id,"
			+ "		fname,"
			+ "		ftitle,"
			+ "		fcooperation_mode,"
			+ "		fpicture_url,"
			+ "		fvideo_url,"
			+ "		fdeeplink,"
			+ "		fh5link,"
			+ "		fdownload_url,"
			+ "		fdevice_max_requests,"
			+ "		ftype,"
			+ "		fstatus,"
			+ "		fctime,"
			+ "		futime"
			+ "	) VALUES ("
			+ "		#{dspBudget.dspPartnerId},"
			+ "		#{dspBudget.name},"
			+ "		#{dspBudget.title},"
			+ "		#{dspBudget.cooperationMode},"
			+ "		#{dspBudget.pictureUrl},"
			+ "		#{dspBudget.videoUrl},"
			+ "		#{dspBudget.deeplink},"
			+ "		#{dspBudget.h5link},"
			+ "		#{dspBudget.downloadUrl},"
			+ "		#{dspBudget.deviceMaxRequests},"
			+ "		#{dspBudget.type},"
			+ "		#{dspBudget.status},"
			+ "		#{dspBudget.ctime},"
			+ "		#{dspBudget.utime}"
			+ "	)")
	int saveDspBudget(@Param("dspBudget") DspBudget dspBudget);

	@Update(" 	UPDATE adx_dsp_budget "
			+ "	SET fname = #{dspBudget.name},"
			+ "		ftitle = #{dspBudget.title},"
			+ "		fcooperation_mode = #{dspBudget.cooperationMode},"
			+ "		fpicture_url = #{dspBudget.pictureUrl},"
			+ "		fvideo_url = #{dspBudget.videoUrl},"
			+ "		fdeeplink = #{dspBudget.deeplink},"
			+ "		fh5link = #{dspBudget.h5link},"
			+ "		fdownload_url = #{dspBudget.downloadUrl},"
			+ "		fdevice_max_requests = #{dspBudget.deviceMaxRequests},"
			+ "		ftype = #{dspBudget.type},"
			+ "		futime = #{dspBudget.utime}"
			+ "	WHERE fid = #{dspBudget.id}")
	int updateDspBudget(@Param("dspBudget") DspBudget dspBudget);

	@Update("	UPDATE adx_dsp_budget "
			+ "	SET fstatus = #{status} "
			+ "	WHERE fid = #{id}")
	int updateDspBudgetStatus(@Param("id") Integer id, @Param("status") Integer status);

	@Delete("DELETE FROM adx_dsp_budget WHERE fid = #{id}")
	int deleteDspBudget(@Param("id") Integer id);



}
