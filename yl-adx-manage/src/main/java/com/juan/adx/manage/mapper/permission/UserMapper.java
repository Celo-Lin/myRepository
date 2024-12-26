package com.juan.adx.manage.mapper.permission;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.juan.adx.model.entity.permission.User;
import com.juan.adx.model.form.manage.UserForm;

@Mapper
public interface UserMapper {
	
	String SQL_COLUMN = " 	fid AS id, "
			+ "				fname AS name, "
			+ "				fposition AS position, "
			+ "				fdesciption AS description, "
			+ "				fctime AS ctime ";
	
	@Select("SELECT " + SQL_COLUMN + " FROM sys_auth_user WHERE fname = #{name} ")
	User getUserByName(@Param("name") String name);

	@Select("<script>"
			+ "	SELECT " + SQL_COLUMN + " FROM sys_auth_user "
			+ "	<where> "
			+ "		<if test=\"form.userId != null and '' != form.userId\"> AND fid LIKE CONCAT(#{form.userId},'%') </if> "
			+ "		<if test=\"form.name != null and '' != form.name\"> AND fname LIKE CONCAT(#{form.name},'%') </if> "
			+ " </where>"
			+ "	ORDER BY fid DESC "
			+ "	</script>")
	List<User> listUser(@Param("form") UserForm paramForm);

	@Select("SELECT " + SQL_COLUMN + " FROM sys_auth_user WHERE fid = #{id} ")
	User getUser(@Param("id") String id);
	
	@Select("SELECT  fpassword AS password, " + SQL_COLUMN + " FROM sys_auth_user WHERE fid = #{id} ")
	User getUserWithPassword(String id);

	@Update("UPDATE sys_auth_user "
			+ " SET fname = #{user.name},"
			+ "		fposition = #{user.position},"
			+ "		fdesciption = #{user.description} "
			+ " WHERE fid = #{user.id}")
	int updateUser(@Param("user") User user);
	
	@Update("UPDATE sys_auth_user "
			+ "	SET fpassword = #{user.password} "
			+ "	WHERE fid = #{user.id}")
	int updatePassword(@Param("user") User user);

	@Insert("INSERT INTO sys_auth_user ("
			+ "	fid,"
			+ "	fname,"
			+ "	fpassword,"
			+ "	fposition,"
			+ "	fdesciption,"
			+ "	fctime"
			+ ") VALUES ("
			+ "	#{user.id},"
			+ "	#{user.name},"
			+ "	#{user.password},"
			+ "	#{user.position},"
			+ "	#{user.description},"
			+ "	#{user.ctime}"
			+ ")")
	int saveUser(@Param("user") User user);

	@Delete("DELETE FROM sys_auth_user WHERE fid = #{id}")
	int deleteUser(@Param("id") String id);


	

}
