package com.juan.adx.manage.mapper.permission;
import java.util.Collection;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.juan.adx.model.entity.permission.AuthResource;

@Mapper
public interface ResourceMapper {
	
	
    @Delete("delete from sys_auth_resource where fid = #{id}")
    int deleteByPrimaryKey(@Param("id") Long id);


    @Insert("<script>" +
            "insert into sys_auth_resource" +
            "    <trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">" +
            "      <if test=\"resource != null\">" +
            "        fresource," +
            "      </if>" +
            "      <if test=\"description != null\">" +
            "        fdescription," +
            "      </if>" +
            "      <if test=\"type != null\">" +
            "        ftype," +
            "      </if>" +
            "      <if test=\"parentid != null\">" +
            "        fparent_id," +
            "      </if>" +
            "      <if test=\"name != null\">" +
            "        fname," +
            "      </if>" +
            "    </trim>" +
            "    <trim prefix=\"values (\" suffix=\")\" suffixOverrides=\",\">" +
            "      <if test=\"resource != null\">" +
            "        #{resource}," +
            "      </if>" +
            "      <if test=\"description != null\">" +
            "        #{description}," +
            "      </if>" +
            "      <if test=\"type != null\">" +
            "        #{type}," +
            "      </if>" +
            "      <if test=\"parentid != null\">" +
            "        #{parentid}," +
            "      </if>" +
            "      <if test=\"name != null\">" +
            "        #{name}," +
            "      </if>" +
            "    </trim>" +
            "</script>")
    int insertSelective(AuthResource record);

    @Select("select fid id, "
    		+ "		fresource resource, fdescription description, ftype `type`, fparent_id parentid, fname `name`" +
            "    from sys_auth_resource" +
            "    where fid = #{id}")
    AuthResource selectByPrimaryKey(Long id);

    @Update("<script>" +
            "update sys_auth_resource" +
            "    <set>" +
            "      <if test=\"resource != null\">" +
            "        fresource = #{resource}," +
            "      </if>" +
            "      <if test=\"description != null\">" +
            "        fdescription = #{description}," +
            "      </if>" +
            "      <if test=\"type != null\">" +
            "        ftype = #{type}," +
            "      </if>" +
            "      <if test=\"parentid != null\">" +
            "        fparent_id = #{parentid}," +
            "      </if>" +
            "      <if test=\"name != null\">" +
            "        fname = #{name}," +
            "      </if>" +
            "    </set>" +
            "    where fid = #{id}"+
            "</script>")
    int updateByPrimaryKeySelective(AuthResource record);

    @Update("<script>" +
            " update sys_auth_resource" +
            "    <set>" +
            "      <if test=\"resource != null\">" +
            "        fresource = #{resource}," +
            "      </if>" +
            "      <if test=\"description != null\">" +
            "        fdescription = #{description}," +
            "      </if>" +
            "      <if test=\"type != null\">" +
            "        ftype = #{type}," +
            "      </if>" +
            "      <if test=\"parentid != null\">" +
            "        fparent_id = #{parentid}," +
            "      </if>" +
            "      <if test=\"name != null\">" +
            "        fname = #{name}," +
            "      </if>" +
            "    </set>" +
            "    where fid = #{id}" +
            "</script>")
    int updateByPrimaryKey(AuthResource record);

    @Select("	select fid AS id, "
    		+ "			fresource AS resource, "
    		+ "			fdescription AS description, "
    		+ "			ftype AS `type`, "
    		+ "			fparent_id AS parentid, "
    		+ "			fname AS `name`, "
    		+ "			ficon AS icon,"
    		+ "			froute AS route"
    		+ "	from sys_auth_resource "
    		+ "	where fparent_id = #{parentid}")
    List<AuthResource> selectByParentid(@Param("parentid") Long parentid);


    @Select(" select count(1)" +
            "    from sys_auth_resource" +
            "    where fname=#{name}")
    Long countByName(@Param("name")String name);


    @Select("	select count(1) "
    		+ "	from sys_auth_resource "
    		+ "	where fresource = #{resource}")
    Long countByResource(@Param("resource")String resource);

    @Select("	<script> "
    		+ "	select 	fid AS id,"
    		+ "			fresource AS resource, "
    		+ "			fdescription AS description, "
    		+ "			ftype AS `type`, "
    		+ "			fparent_id AS parentid, "
    		+ "			fname AS `name`, "
    		+ "			forder AS `order`,"
    		+ "			ficon AS icon,"
    		+ "			froute AS route"
    		+ "	from sys_auth_resource "
    		+ "	where fid in "
    		+ "		<foreach item=\"item\" index=\"index\" collection=\"idCollection\" open=\"(\" separator=\",\" close=\")\"> "
    		+ "			#{item} "
    		+ "		</foreach> "
    		+ "	</script>")
	List<AuthResource> selectByIdIn(@Param("idCollection")Collection<Long> idCollection);










}