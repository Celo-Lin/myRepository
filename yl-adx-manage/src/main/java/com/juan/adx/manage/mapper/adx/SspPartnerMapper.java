package com.juan.adx.manage.mapper.adx;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.juan.adx.model.dto.manage.SspPartnerOptionDto;
import com.juan.adx.model.entity.manage.SspPartner;
import com.juan.adx.model.form.manage.SspPartnerForm;

@Mapper
public interface SspPartnerMapper {
	

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
			+ "		FROM adx_ssp_partner "
			+ "		<where> "
			+ "			<if test=\"form.name != null and form.name != '' \"> "
			+ "				AND fname like concat(#{form.name},'%')"
			+ "			</if> "
			+ "		</where> "
			+ "		ORDER BY fid DESC "
			+ "	</script>")
	List<SspPartner> querySspPartnerList(@Param("form") SspPartnerForm form);

	@Select("	SELECT " + SQL_COLUMN
			+ "	FROM adx_ssp_partner "
			+ "	WHERE fid = #{id} ")
	SspPartner querySspPartner(@Param("id") Integer id);
	
	@Select("	<script>"
			+ "		SELECT 	fid AS id,"
			+ "				fname AS name"
			+ "		FROM adx_ssp_partner "
			+ "		<where> "
			+ "			<if test=\"name != null and name != '' \"> "
			+ "				AND fname like concat('%', #{name}, '%')"
			+ "			</if> "
			+ "		</where> "
			+ "		ORDER BY fid DESC "
			+ "	</script>")
	List<SspPartnerOptionDto> querySspPartnerOption(@Param("name") String name);

	@Insert("	INSERT INTO adx_ssp_partner("
			+ "		fname,"
			+ "		fcompany,"
			+ "		fcontact_name,"
			+ "		fphone,"
			+ "		fstatus,"
			+ "		fctime,"
			+ "		futime"
			+ "	) VALUES ("
			+ "		#{sspPartner.name},"
			+ "		#{sspPartner.company},"
			+ "		#{sspPartner.contactName},"
			+ "		#{sspPartner.phone},"
			+ "		#{sspPartner.status},"
			+ "		#{sspPartner.ctime},"
			+ "		#{sspPartner.utime}"
			+ ")")
	int saveSspPartner(@Param("sspPartner") SspPartner sspPartner);

	@Update("	UPDATE adx_ssp_partner "
			+ "	SET fcontact_name = #{sspPartner.contactName}, "
			+ "		fphone = #{sspPartner.phone}, "
			+ "		futime = #{sspPartner.utime} "
			+ "	WHERE fid = #{sspPartner.id} ")
	int updateSspPartner(@Param("sspPartner") SspPartner sspPartner);

	@Delete(" DELETE FROM adx_ssp_partner WHERE fid = #{id}")
	int deleteSspPartner(@Param("id") Integer id);



}
