package com.juan.adx.task.quartz;

import org.quartz.Trigger;

/**
 * 
 * @Description NONE(没有该job), NORMAL(正常), PAUSED(暂停), COMPLETE(完成), ERROR(错误), BLOCKED(阻塞) 
 *
 */
public class ScheduleState{
	
	/**状态：NONE*/
	public static final String STATUS_NONE = Trigger.TriggerState.NONE.name();
	
	/**状态：NORMAL*/
	public static final String STATUS_NORMAL = Trigger.TriggerState.NORMAL.name();
	
	/**状态：PAUSED*/
	public static final String STATUS_PAUSED = Trigger.TriggerState.PAUSED.name();
	
	public static final String STATUS_COMPLETE = Trigger.TriggerState.COMPLETE.name();
	
	public static final String STATUS_ERROR = Trigger.TriggerState.ERROR.name();
	
	public static final String STATUS_BLOCKED = Trigger.TriggerState.BLOCKED.name();
	
	
	public static final Integer CONCURRENT_YES = 1;
	public static final Integer CONCURRENT_NO = 0;
	
}

