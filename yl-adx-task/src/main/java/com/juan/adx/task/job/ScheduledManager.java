package com.juan.adx.task.job;

public class ScheduledManager {
	
	/**	@Scheduled所支持的参数
	 	1.cron：cron表达式，指定任务在特定时间执行；
		2.fixedDelay：表示上一次任务执行完成后多久再次执行，参数类型为long，单位ms；
		3.fixedDelayString：与fixedDelay含义一样，只是参数类型变为String；
		4.fixedRate：表示按一定的频率执行任务，参数类型为long，单位ms；
		5.fixedRateString: 与fixedRate的含义一样，只是将参数类型变为String；
		6.initialDelay：表示延迟多久再第一次执行任务，参数类型为long，单位ms；
		7.initialDelayString：与initialDelay的含义一样，只是将参数类型变为String；
		8.zone：时区，默认为当前时区，一般没有用到
	 */
	//Cron表达式范例：
	//每隔5秒执行一次：*/5 * * * * ?
	//每隔1分钟执行一次：0 */1 * * * ?
	//每隔1小时执行一次：0 0 /1 * * ? 
	//每天23点执行一次：0 0 23 * * ?
	//每天凌晨1点执行一次：0 0 1 * * ?
	//每月1号凌晨1点执行一次：0 0 1 1 * ?
	//每月最后一天23点执行一次：0 0 23 L * ?
	//每周星期天凌晨1点实行一次：0 0 1 ? * L
	//在26分、29分、33分执行一次：0 26,29,33 * * * ?
	//每天的0点、13点、18点、21点都执行一次：0 0 0,13,18,21 * * ?
	
}
