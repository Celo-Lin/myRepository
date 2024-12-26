package com.juan.adx.common.utils;

import java.lang.management.ManagementFactory;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

import com.eaio.uuid.UUIDGen;

import lombok.extern.slf4j.Slf4j;

/**
 * 基于Twitter的snowflake理论,添加MAC+PID的hash值作为workerId:
 * (a) id构成: 39位的时间前缀 + 16位的节点标识 + 9位的sequence避免并发的数字(9位不够用时强制得到新的时间前缀)
 * (b) 对系统时间的依赖性非常强，最好关闭ntp的时间同步功能。当检测到ntp时间调整后，会等待时间校准
 * 
 */
@Slf4j
public class SnowFlake {
	
	private static final long DEFAULT_WORKER_ID;

	/**
     * mac
     */
    private final static long clockSeqAndNode = UUIDGen.getClockSeqAndNode();
    /**
     * macPidHashCode的16个低位取值
     */
    private final static long workerId;
    
    /**
     * mac+pid的hashcode
     */
    private final static long macPidHashCode;

    /**
     * 时间起始标记点，作为基准，一般取系统第一次运行的的时间毫秒值作为"新纪元:2017-09-04 00:00:00" 
     */
    private final long epoch = 1504454400000L;
    /**
     * 毫秒内序列
     */
    private long sequence = 0L;
    /**
     * 毫秒内自增位数
     */
    private final long sequenceBits = 9L;
    /**
     * 毫秒内最大自增序列值:511,9位
     */
    private final long sequenceMax = -1L ^ -1L << this.sequenceBits;
    /**
     * worker标识位数
     */
    private final long workerIdBits = 16L;
    /**
     * workerId左移动位
     */
    private final long workerIdLeftShift = this.sequenceBits;
    /**
     * 时间戳左移动位
     */
    private final long timestampLeftShift = this.sequenceBits + this.workerIdBits;
    /**
     * 上次生产id时间戳
     */
    private long lastTimestamp = -1L;
    
    private final ReentrantLock lock = new ReentrantLock();

    static {
        macPidHashCode = (clockSeqAndNode + "" + getJvmPid()).hashCode();
        DEFAULT_WORKER_ID = macPidHashCode & 0xffff;//获取16个低位
        workerId = getWorkerId();
    }

    private SnowFlake() {
    	
    }
    
    private static SnowFlake snowFlake = new SnowFlake();

    public static SnowFlake getInstance() {
        return snowFlake;
    }

    /**
     * 获取pid
     * @return pid
     */
    private static String getJvmPid() {
        String name = ManagementFactory.getRuntimeMXBean().getName();
        String pid = name.split("@")[0];
        return pid;
    }
    
    /**
     * 获取工作节点ID
     * @return
     */
    private static long getWorkerId() {
        long workerId = DEFAULT_WORKER_ID;
        String workerIdStr = System.getProperty("workerId");
        if (workerIdStr != null) {
            try {
                workerId = Long.parseLong(workerIdStr);
                if (workerId < 0 || workerId > ((1L << 16L) - 1)) {
                    throw new IllegalArgumentException("Invalid worker ID");
                }
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid worker ID");
            }
        }
        return workerId;
    }
    
    /**
     * 获取uniqueId(long)
     *
     * @return id
     * @throws Exception
     */
    public long nextId() {
    	lock.lock();
    	try {
    		long timestamp = this.timeGen();
            if (timestamp < this.lastTimestamp) {
            	timestamp = afterNTP(this.lastTimestamp);
                log.error("clock moved backwards.Refusing to generate id for {} milliseconds", (this.lastTimestamp - timestamp));
            }
            if (this.lastTimestamp == timestamp) { // 如果上一个timestamp与新产生的相等，则sequence加一(0-511循环); 对新的timestamp，sequence从0开始
            	this.sequence = (this.sequence + 1) & this.sequenceMax;
                if (this.sequence == 0) {
                    timestamp = this.waitingNextMillis(this.lastTimestamp);// 重新生成timestamp
                }
            } else {
                this.sequence = 0;
            }
            this.lastTimestamp = timestamp;
            long id = ((timestamp - this.epoch) << this.timestampLeftShift) | (SnowFlake.workerId << this.workerIdLeftShift) | this.sequence;
            return id;
		} finally {
			lock.unlock();
		}
    }
    
    /**
     * 当NTP时间调整后,导致时间回溯时,将一直获取新的时间,直至超过最后获取到的时间
     *
     * @return timestamp
     */
    private long afterNTP(long lastTimestamp) {
        // 当检测到ntp时间调整后，将一直获取新的时间,直至超过最后获取到的时间
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            // 默认每1毫秒检测一次时间,防止CPU满载
            try {
                Thread.sleep(1l);
            } catch (InterruptedException e) {
            	Thread.currentThread().interrupt();
                e.printStackTrace();
            }
            timestamp = timeGen();
        }
        return timestamp;
    }
    

    /**
     * 等待下一个毫秒的到来, 保证返回的毫秒数在参数lastTimestamp之后
     */
    private long waitingNextMillis(long lastTimestamp) {
        long timestamp = this.timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = this.timeGen();
        }
        return timestamp;
    }

    /**
     * 获得系统当前毫秒数
     */
    private long timeGen() {
        return LocalDateUtils.getNowMilliseconds();
    }

    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        Set<Long> set = new HashSet<Long>();
        for (int i = 0; i < 1000000; i++) {
            long nextId = SnowFlake.getInstance().nextId();
            if (set.contains(nextId)) {
            	System.out.println("**************有重复**************");
            } else {
                set.add(nextId);
                System.out.println(nextId);
            }
        }
        System.out.println("Elapsed time : " + (System.currentTimeMillis() - start));
    }
}
