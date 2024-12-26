package com.juan.adx.task.quartz;

import java.util.Map;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.juan.adx.model.entity.ScheduleJob;

/**
 * 任务调度管理
 */
@Component
public class QuartzManager {

    @Autowired
    private Scheduler scheduler;
    
    /**
     * @Description: 添加一个定时任务
     *
     * @param jobName 任务名
     * @param jobGroupName  任务组名
     * @param triggerName 触发器名
     * @param triggerGroupName 触发器组名
     * @param jobClass  任务
     * @param cron   时间设置，参考quartz说明文档
     * @param jobMap 参数
     */
    public  void addJob(String jobName, String jobGroupName,
                              String triggerName, String triggerGroupName, Class<? extends Job> jobClass,
                              String cron, Map<String,Object> jobMap) {
        try {
            JobDetail jobDetail= JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroupName).build();
            if(jobMap!=null){
                for(String key:jobMap.keySet()){
                    jobDetail.getJobDataMap().put(key,jobMap.get(key));
                }
            }
            jobDetail.getJobDataMap().put("jobName",jobName);
            TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
            triggerBuilder.withIdentity(triggerName, triggerGroupName);
            triggerBuilder.startNow();
            triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cron));
            CronTrigger trigger = (CronTrigger) triggerBuilder.build();
            scheduler.scheduleJob(jobDetail, trigger);
            // 启动
            if (!scheduler.isShutdown()) {
                scheduler.start();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
	public void addJob(ScheduleJob scheduleJob, Class<? extends Job> jobClass) {
		try {
			String jobName = scheduleJob.getJobName();
			String jobGroupName = scheduleJob.getJobGroupName();
			JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroupName).build();
			jobDetail.getJobDataMap().put("scheduleJob", scheduleJob);
			TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
			triggerBuilder.withIdentity(jobName, jobGroupName);
			triggerBuilder.startNow();
			triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(scheduleJob.getCron()));
			CronTrigger trigger = (CronTrigger) triggerBuilder.build();
			scheduler.scheduleJob(jobDetail, trigger);
			if (!scheduler.isShutdown()) {
				scheduler.start();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

    /**
     * @Description: 修改一个任务的触发时间
     *
     * @param jobName
     * @param jobGroupName
     * @param triggerName 触发器名
     * @param triggerGroupName 触发器组名
     * @param cron   时间设置，参考quartz说明文档
     */
    public  void modifyJobTime(String jobName, String jobGroupName, String triggerName, String triggerGroupName, String cron) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            if (trigger == null) {
                return;
            }

            String oldTime = trigger.getCronExpression();
            if (!oldTime.equalsIgnoreCase(cron)) {
                TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
                triggerBuilder.withIdentity(triggerName, triggerGroupName);
                triggerBuilder.startNow();
                triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cron));
                trigger = (CronTrigger) triggerBuilder.build();
                scheduler.rescheduleJob(triggerKey, trigger);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @Description: 移除一个任务
     *
     * @param jobName
     * @param jobGroupName
     * @param triggerName
     * @param triggerGroupName
     */
    @SuppressWarnings("unused")
	public void removeJob(String jobName, String jobGroupName,
                                 String triggerName, String triggerGroupName) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);
            if(triggerKey!=null){
                scheduler.pauseTrigger(triggerKey);// 停止触发器
                boolean unscheduleJob = scheduler.unscheduleJob(triggerKey);// 移除触发器
                boolean deleteJob = scheduler.deleteJob(JobKey.jobKey(jobName, jobGroupName));// 删除任务
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @Description:启动所有定时任务
     */
    public  void startJobs() {
        try {
            scheduler.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @Description:关闭所有定时任务
     */
    public void shutdownJobs() {
        try {
            if (!scheduler.isShutdown()) {
                scheduler.shutdown();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @Description	判断当前job是否存在
     * 
     * @param triggerName
     * @param triggerGroupName
     * @return NONE(没有该job), NORMAL(正常), PAUSED(暂停), COMPLETE(完成), ERROR(错误), BLOCKED(阻塞);
     */
    public String getJobState(String triggerName, String triggerGroupName){
        String flag = null;
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);
            Trigger.TriggerState state = scheduler.getTriggerState(triggerKey);
            flag = state.toString();

        }catch (Exception e){
            flag = "ERROR";
        }
        return flag;
    }

}
