package com.juan.adx.manage.mapper.permission;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.juan.adx.model.entity.permission.AuthResource;
import com.juan.adx.model.entity.permission.AuthRoleResource;

@Mapper
public interface RoleResourceMapper {

	@Select(" 	SELECT fresource_id AS resourceId "
			+ "	FROM sys_auth_role_resource "
			+ "	WHERE frole_id = #{id} ")
	List<Long> getResourceIdsByRoleid( @Param( "id" ) long id );

	/**
	 * 根据角色ID删除
	 */
	@Delete("DELETE FROM sys_auth_role_resource WHERE frole_id = #{id} ")
	void deleteResByRid( @Param( "id" ) long id );

	/**
	 * 根据资源ID删除
	 */
	@Delete(" DELETE FROM sys_auth_role_resource WHERE fresource_id = #{id} ")
	void deleteByResId( @Param( "id" ) long id );

	/**
	 * 根据角色ID和资源ID删除
	 * @param resId
	 * @param rId
	 */
	@Delete(" DELETE FROM sys_auth_role_resource WHERE fresource_id = #{resId} AND frole_id = #{rId}")
	void deleteByResIdAndRid( @Param( "resId" ) long resId, @Param( "rId" ) long rId );

	@Insert(" INSERT IGNORE INTO sys_auth_role_resource("
			+ "	frole_id,"
			+ "	fresource_id"
			+ ") VALUES("
			+ "	#{role.roleId},"
			+ "	#{role.resourceId}"
			+ ") ")
	void saveRoleRes( @Param( "role" ) AuthRoleResource role );

	@Select(" 	<script>"
			+ "	SELECT 	DISTINCT(R.fid) AS id, "
			+ "			R.fresource AS resource, "
			+ "			R.fdescription AS description, "
			+ "			R.ftype AS `type`, "
			+ "			R.fparent_id AS parentId,"
			+ "			R.fname AS `name`,"
			+ "			R.forder AS `order`,"
			+ "			R.furl AS url,"
			+ "			R.ficon AS icon,"
			+ "			R.froute AS route"
			+ "	FROM sys_auth_resource R LEFT JOIN sys_auth_role_resource RR ON R.fid = RR.fresource_id "
			+ "		LEFT JOIN sys_auth_role RL ON RR.frole_id=RL.fid "
			+ "	<where>"
			+ "		<if test=\"roleIds != null and roleIds.size > 0\" >"
			+ "	 		AND RR.frole_id in "
			+ "			<foreach collection=\"roleIds\" item=\"roleId\" separator=\",\" open=\"(\" close=\")\">"
			+ "				#{roleId}"
			+ "			</foreach> "
			+ "		</if>"
			+ "	</where> "
			+ "	</script>")
	List<AuthResource> getResources(@Param( "roleIds" ) List<Long> roleIds );
}
