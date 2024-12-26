package com.juan.adx.api.config.concurrent;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import lombok.Getter;
import lombok.Setter;

@Configuration
public class ExecutorConfig {

	/**
	 *	 核心线程数，默认为1
	 */
	@Getter
	@Setter
	@Value("${adx.executor.core.pool.size:4}")
	private int corePoolSize;
	
	/**
	 * 	最大线程数, 默认为Integer.MAX_VALUE
	 */
	@Getter
	@Setter
	@Value("${adx.executor.max.pool.size:300}")
	private int maxPoolSize;
	
	/**
	 * 	队列最大长度,默认为Integer.MAX_VALUE
	 */
	@Getter
	@Setter
	@Value("${adx.executor.queue.capacity:100}")
	private int queueCapacity;
	
	/**
	 * 线程的最大空闲时间,默认值: 60秒
	 */
	@Getter
	@Setter
	@Value("${adx.executor.keep.alive.seconds:120}")
	private int keepAliveSeconds;
	
	
	/**
	 * 	自定义异步线程池
	 */
	@Bean("apiExecutor")
    public Executor apiExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(corePoolSize);
		executor.setMaxPoolSize(maxPoolSize);
		executor.setQueueCapacity(queueCapacity);
		executor.setKeepAliveSeconds(keepAliveSeconds);
		executor.setThreadNamePrefix("ADX-API-ASYNC-");
		executor.setWaitForTasksToCompleteOnShutdown(true);
	    executor.setAwaitTerminationSeconds(60);
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		/*executor.setRejectedExecutionHandler(new RejectedExecutionHandler() {
			//重写CallerRunsPolicy策略
			@Override
			public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
				
				log.info("rejected execution: {}", r.toString());
				
	            if (!e.isShutdown()) {
	                r.run();
	            }
	        }
			
		});*/
		executor.initialize();
        return executor;
    }

	
}
