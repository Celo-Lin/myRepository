package com.juan.adx.manage.mapper.permission;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.juan.adx.model.entity.permission.RoleUserSimple;


@Mapper
public interface UserRoleMapper {

	@Select(" 	SELECT frole_id roleId "
			+ "	FROM sys_auth_user_role "
			+ "	WHERE fuser_id = #{uid} "
			+ "	ORDER BY frole_id")
	List<Long> getRolesByUid( @Param( "uid" ) String uid );

	/**
	 * 通过角色id查询用户列表
	 * @param id
	 * @return
	 */
	@Select(" 	SELECT fuser_id userId "
			+ "	FROM sys_auth_user_role "
			+ "	WHERE frole_id=#{id}")
	List<String> getUsersByRoleId( @Param( "id" ) long id );


	@Delete(" 	DELETE FROM sys_auth_user_role "
			+ "	WHERE frole_id= #{id} ")
	void deleteByRid( @Param( "id" ) long id );

	/**
	 * 新增用户角色关系
	 */
	@Insert({"<script>"," INSERT IGNORE INTO sys_auth_user_role(fuser_id,frole_id) VALUES " +
			"<foreach collection=\"userIds\" item=\"userId\" index=\"index\" separator=\",\">" +
			"	(" +
			"		#{userId},#{roleId}" +
			"	) " +
			"</foreach>","</script>"})
	void saveUserRole( @Param( "roleId" )Long roleId, @Param( "userIds" ) List<String> userIds);

	/**
	 * 删除用户角色关系
	 * @param clientId
	 */
	@Delete({"<script>",
			"<foreach collection=\"userIds\" item=\"userId\" index=\"index\" separator=\";\">" +
			" 	DELETE FROM sys_auth_user_role WHERE fuser_id = #{userId} AND frole_id=#{roleId} " +
			"</foreach>","</script>"})
	void deleteUserRole( @Param( "roleId" ) Long roleId, @Param( "userIds" ) List<String> userIds );

	@Delete("	DELETE FROM sys_auth_user_role "
			+ "	WHERE fuser_id = #{uid} ")
	int deleteUserRoleByUid(@Param("uid") String uid);
	
	/**
	 * 查询绑定角色的所有用户信息
	 * @param roleId
	 * @return
	 */
	@Select("	SELECT  U.fid AS userId,"
			+ "			U.fname AS userName,"
			+ "			U.fposition AS postName,"
			+ "			U.fdesciption AS description"
			+ "	FROM sys_auth_user AS U LEFT JOIN sys_auth_user_role AS UR ON U.fid = UR.fuser_id "
			+ "	WHERE UR.frole_id = #{roleId} ")
	List<RoleUserSimple> getUserInfoByRoleId(@Param( "roleId" ) long roleId );


}
