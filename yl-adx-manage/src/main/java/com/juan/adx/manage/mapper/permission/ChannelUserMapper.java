package com.juan.adx.manage.mapper.permission;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.juan.adx.model.dto.sspmanage.ChannelUserDto;
import com.juan.adx.model.entity.sspmanage.ChannelUser;
import com.juan.adx.model.form.sspmanage.ChannelUserForm;

@Mapper
public interface ChannelUserMapper {
	
	String SQL_COLUMN = " 	fid AS id, "
			+ "				faccount_id AS accountId, "
			+ "				fname AS name, "
			+ "				fssp_partner_id AS sspPartnerId, "
			+ "				fstatus AS status, "
			+ "				fctime AS ctime, "
			+ "				futime AS utime ";

	@Select("	<script>"
			+ " 	SELECT " + SQL_COLUMN
			+ "		FROM sys_auth_channel_user "
			+ "		<where> "
			+ "			<if test=\"form.accountId != null and form.accountId > 0 \"> "
			+ "				AND faccount_id = #{form.accountId} "
			+ "			</if> "
			+ "			<if test=\"form.name != null and form.name != '' \"> "
			+ "				AND fname like concat(#{form.name},'%')"
			+ "			</if> "
			+ "		</where> "
			+ "		ORDER BY fid DESC "			
			+ "	</script>")
	List<ChannelUserDto> queryChannelUserList(@Param("form") ChannelUserForm form);

	@Select("	SELECT " + SQL_COLUMN
			+ "	FROM sys_auth_channel_user "
			+ "	WHERE fid = #{userId}")
	ChannelUserDto queryChannelUser(@Param("userId") Integer userId);
	
	@Select("	SELECT COUNT(1) "
			+ "	FROM sys_auth_channel_user "
			+ "	WHERE faccount_id = #{accountId}")
	int countChannelUserByAccountId(@Param("accountId") String accountId);

	@Update("	UPDATE sys_auth_channel_user "
			+ "	SET fname = #{channelUser.name}"
			+ "	WHERE fid = #{channelUser.id}")
	int updateChannelUser(@Param("channelUser") ChannelUser channelUser);

	@Update("	UPDATE sys_auth_channel_user "
			+ "	SET fpassword = #{channelUser.password}"
			+ "	WHERE fid = #{channelUser.id}")
	int updatePassword(@Param("channelUser") ChannelUser channelUser);

	@Update("	UPDATE sys_auth_channel_user "
			+ "	SET fstatus = #{status}"
			+ "	WHERE fid = #{userId}")
	int updateStatus(@Param("userId") Integer userId, @Param("status") Integer status);

	@Insert("	INSERT INTO sys_auth_channel_user ("
			+ "		faccount_id,"
			+ "		fpassword,"
			+ "		fname,"
			+ "		fssp_partner_id,"
			+ "		fstatus,"
			+ "		fctime,"
			+ "		futime"
			+ "	) VALUES ("
			+ "		#{channelUser.accountId},"
			+ "		#{channelUser.password},"
			+ "		#{channelUser.name},"
			+ "		#{channelUser.sspPartnerId},"
			+ "		#{channelUser.status},"
			+ "		#{channelUser.ctime},"
			+ "		#{channelUser.utime}"
			+ ")")
	int saveChannelUser(@Param("channelUser") ChannelUser channelUser);



}
