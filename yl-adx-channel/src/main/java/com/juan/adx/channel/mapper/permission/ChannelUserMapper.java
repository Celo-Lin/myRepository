package com.juan.adx.channel.mapper.permission;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.juan.adx.model.entity.sspmanage.ChannelUser;

@Mapper
public interface ChannelUserMapper {
	
	String SQL_COLUMN = " 	fid AS id, "
			+ "				faccount_id AS accountId, "
			+ "				fname AS name, "
			+ "				fssp_partner_id AS sspPartnerId, "
			+ "				fstatus AS status, "
			+ "				fctime AS ctime, "
			+ "				futime AS utime ";
	

	
	@Select("	SELECT  fpassword AS password, " + SQL_COLUMN
			+ "	FROM sys_auth_channel_user "
			+ "	WHERE faccount_id = #{accountId} ")
	ChannelUser queryUserWithPasswordByAccountId(@Param("accountId") String accountId);
	
	@Select("	SELECT  fpassword AS password, " + SQL_COLUMN
			+ "	FROM sys_auth_channel_user "
			+ "	WHERE fssp_partner_id = #{sspPartnerId} AND fid = #{userId} ")
	ChannelUser queryChannelUserWithPassword(@Param("sspPartnerId") Integer sspPartnerId, @Param("userId") Integer userId);
	
	@Update("	UPDATE sys_auth_channel_user "
			+ "	SET fpassword = #{user.password} "
			+ "	WHERE fssp_partner_id = #{user.sspPartnerId} AND fid = #{user.id}")
	int updatePassword(@Param("user") ChannelUser user);


	

}
