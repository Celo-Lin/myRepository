package com.juan.adx.manage.mapper.permission;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.juan.adx.model.entity.ScheduleJob;

@Mapper
public interface SchedulerMapper {

    String SQL_COLUMN = " 	fid AS id, "
    		+ "				fjob_name AS jobName, "
    		+ "				fjob_group_name AS jobGroupName, "
    		+ "				fstatus AS status, "
    		+ "				finstance_name AS instanceName, "
    		+ "				fconcurrent AS concurrent, "
    		+ "				frestart AS restart, "
    		+ "				fcron AS cron, "
    		+ "				frestart_time AS restartTime, "
    		+ "				flast_run_time AS lastRunTime";

    /**
     * 获取所有定时任务
     * @return
     */
    @Select("SELECT " + SQL_COLUMN + " FROM adx_schedule")
    List<ScheduleJob> allScheduleJob();

    /**
     * 更新定时任务
     * @param id
     * @param type
     * @return
     */
    @Update("	UPDATE adx_schedule "
    		+ "	SET frestart = #{type} "
    		+ "	WHERE FID = #{id}")
    int updateRestart(@Param("id") Integer id, @Param("type") int type);

}
