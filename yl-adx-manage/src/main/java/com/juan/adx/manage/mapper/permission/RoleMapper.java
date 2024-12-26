package com.juan.adx.manage.mapper.permission;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.juan.adx.model.entity.permission.AuthRole;
import com.juan.adx.model.form.manage.RoleForm;

@Mapper
public interface RoleMapper {

	@Select("	<script>"
			+ "	SELECT 	fid id, "
			+ "			fdescription description, "
			+ "			fname 'name' "
			+ "	FROM sys_auth_role "
			+ "	<where> "
			+ "	<if test=\"form.name!=null and '' !=form.name\"> and fname like concat(#{form.name},'%')</if> </where> "
			+ "	ORDER BY fid DESC "
			+ "	</script>")
	List<AuthRole> getRoles( @Param( "form" ) RoleForm form );
	
	@Select(" 	SELECT COUNT(*) "
			+ "	FROM sys_auth_role "
			+ "	WHERE fname = #{name} ")
	int checkRoleByName( @Param( "name" ) String name );
	
	@Select(" 	SELECT 	fid id, "
			+ "			fdescription description, "
			+ "			fname name "
			+ "	FROM sys_auth_role "
			+ "	WHERE fid =#{id} ")
	AuthRole getRoleById( @Param( "id" ) Long id );

	@Insert(" 	INSERT INTO sys_auth_role ("
			+ "		fname"
			+ "	) VALUES ("
			+ "		#{role.name}"
			+ "	) ")
	void addRole( @Param( "role" ) AuthRole role );

	@Delete(" 	DELETE FROM sys_auth_role "
			+ "	WHERE fid= #{id} ")
	void deleteRole( @Param( "id" ) Long id );

	@Update(" 	UPDATE sys_auth_role "  
			+ "	SET	fname=#{role.name} "
			+ "	WHERE fid=#{role.id} ")
	void updateRole( @Param( "role" ) AuthRole role );
	
}
