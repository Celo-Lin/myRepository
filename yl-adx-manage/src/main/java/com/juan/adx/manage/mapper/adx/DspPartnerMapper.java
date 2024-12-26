package com.juan.adx.manage.mapper.adx;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.juan.adx.model.dto.manage.DspPartnerOptionDto;
import com.juan.adx.model.entity.manage.DspPartner;
import com.juan.adx.model.form.manage.DspPartnerForm;

@Mapper
public interface DspPartnerMapper {
	
	String SQL_COLUMN = "	fid AS id,"
			+ "				fname AS name,"
			+ "				fcompany AS company,"
			+ "				fcontact_name AS contactName,"
			+ "				fphone AS phone,"
			+ "				fstatus AS status,"
			+ "				fctime AS ctime,"
			+ "				futime AS utime";

	
	@Select("	<script>"
			+ "		SELECT " + SQL_COLUMN 
			+ "		FROM adx_dsp_partner "
			+ "		<where> "
			+ "			<if test=\"form.name != null and form.name != '' \"> "
			+ "				AND fname like concat('%', #{form.name},'%')"
			+ "			</if> "
			+ "		</where> "
			+ "		ORDER BY fid DESC "
			+ "	</script>")
	List<DspPartner> queryDspPartnerList(@Param("form") DspPartnerForm form);

	@Select("	<script>"
			+ "		SELECT 	fid AS id,"
			+ "				fname AS name"
			+ "		FROM adx_dsp_partner "
			+ "		<where> "
			+ "			<if test=\"name != null and name != '' \"> "
			+ "				AND fname like concat('%', #{name}, '%')"
			+ "			</if> "
			+ "		</where> "
			+ "		ORDER BY fid DESC "
			+ "	</script>")
	List<DspPartnerOptionDto> queryDspPartnerOption(@Param("name") String name);

	@Insert("	INSERT INTO adx_dsp_partner("
			+ "		fname,"
			+ "		fcompany,"
			+ "		fcontact_name,"
			+ "		fphone,"
			+ "		fstatus,"
			+ "		fctime,"
			+ "		futime"
			+ "	) VALUES ("
			+ "		#{dspPartner.name},"
			+ "		#{dspPartner.company},"
			+ "		#{dspPartner.contactName},"
			+ "		#{dspPartner.phone},"
			+ "		#{dspPartner.status},"
			+ "		#{dspPartner.ctime},"
			+ "		#{dspPartner.utime}"
			+ ")")
	int saveDspPartner(@Param("dspPartner") DspPartner dspPartner);

	@Select("	SELECT " + SQL_COLUMN 
			+ "	FROM adx_dsp_partner"
			+ "	WHERE fid = #{id} "
			+ "	ORDER BY fid DESC ")
	DspPartner queryDspPartner(@Param("id") Integer id);

	@Update("	UPDATE adx_dsp_partner "
			+ "	SET fcontact_name = #{dspPartner.contactName}, "
			+ "		fphone = #{dspPartner.phone}, "
			+ "		futime = #{dspPartner.utime} "
			+ "	WHERE fid = #{dspPartner.id} ")
	int updateDspPartner(@Param("dspPartner") DspPartner dspPartner);

	@Delete(" DELETE FROM adx_dsp_partner WHERE fid = #{id}")
	int deleteDspPartner(@Param("id") Integer id);

}
