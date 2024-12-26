package com.juan.adx.common.utils;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class LocalDateUtils {
	
	/**
	 * yyyyMMddHHmmss
	 */
	public static final DateTimeFormatter DATETIME_PLAIN_FORMATTER;
    static {
    	DATETIME_PLAIN_FORMATTER = DateTimeFormatter.ofPattern(DatePatternUtils.DATE_TIME_PLAIN);
    }
    
    /**
     * yyyyMMddHH
     */
    public static final DateTimeFormatter DATE_HOUR_PLAIN_FORMATTER;
    static {
    	DATE_HOUR_PLAIN_FORMATTER = DateTimeFormatter.ofPattern(DatePatternUtils.DATE_HOUR_PLAIN);
    }
    
	
	/**
	 * yyyyMMdd
	 */
	public static final DateTimeFormatter DATE_PLAIN_FORMATTER;
    static {
    	DATE_PLAIN_FORMATTER = DateTimeFormatter.ofPattern(DatePatternUtils.DATE_PLAIN);
    }
    
	/**
	 * yyyyMM
	 */
	public static final DateTimeFormatter DATE_MONTH_PLAIN_FORMATTER;
    static {
    	DATE_MONTH_PLAIN_FORMATTER = DateTimeFormatter.ofPattern(DatePatternUtils.DATE_MONTH_PLAIN);
    }
    
	/**
	 * yyyy-MM
	 */
	public static final DateTimeFormatter DATE_MONTH_FORMATTER;
    static {
    	DATE_MONTH_FORMATTER = DateTimeFormatter.ofPattern(DatePatternUtils.DATE_MONTH);
    }
    
	/**
	 * yyyy-MM-dd
	 */
	public static final DateTimeFormatter DATE_FORMATTER;
    static {
    	DATE_FORMATTER = DateTimeFormatter.ofPattern(DatePatternUtils.DATE);
    }
    
    /**
	 * yyyy-MM-dd HH
	 */
	public static final DateTimeFormatter DATE_HOUR_FORMATTER;
    static {
    	DATE_HOUR_FORMATTER = DateTimeFormatter.ofPattern(DatePatternUtils.DATE_HOUR);
    }

    /**
	 * yyyy-MM-dd HH:mm
	 */
	public static final DateTimeFormatter DATE_HOUR_MINUTE_FORMATTER;
    static {
    	DATE_HOUR_MINUTE_FORMATTER = DateTimeFormatter.ofPattern(DatePatternUtils.DATE_HOUR_MINUTE);
    }
    
    /**
	 * yyyy-MM-dd HH:mm:ss
	 */
	public static final DateTimeFormatter DATE_TIME_FORMATTER;
    static {
    	DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DatePatternUtils.DATE_TIME);
    }
    
    
    
    
    
    
	/**
	 * 	将long类型的milliseconds转为LocalDateTime
	 * @param milliseconds
	 * @return
	 */
	public static LocalDateTime parseMillisecondsToLocalDateTime(long milliseconds) {
	    Instant instant = Instant.ofEpochMilli(milliseconds);
	    ZoneId zone = ZoneId.systemDefault();
	    return LocalDateTime.ofInstant(instant, zone);
	}
	
	/**
	 * 	将long类型的milliseconds转为LocalDate
	 * @param milliseconds
	 * @return
	 */
	public static LocalDate parseMillisecondsToLocalDate(long milliseconds) {
		/*LocalDate localDate = Instant.ofEpochMilli(milliseconds).atZone(ZoneOffset.ofHours(8)).toLocalDate();*/
		 Instant instant = Instant.ofEpochMilli(milliseconds);
		 ZoneId zone = ZoneId.systemDefault();
		 return instant.atZone(zone).toLocalDate();
	}
	
	/**
	 * 	将long类型的seconds转为LocalDateTime
	 * @param seconds
	 * @return
	 */
	public static LocalDateTime parseSecondsToLocalDateTime(long seconds) {
		Instant instant = Instant.ofEpochSecond(seconds);
		ZoneId zone = ZoneId.systemDefault();
		return instant.atZone(zone).toLocalDateTime();
	}
	
	/**
	 * 	将long类型的seconds转为LocalDate
	 * @param seconds
	 * @return
	 */
	public static LocalDate parseSecondsToLocalDate(long seconds) {
		Instant instant = Instant.ofEpochSecond(seconds);
		ZoneId zone = ZoneId.systemDefault();
		return instant.atZone(zone).toLocalDate();
	}
	
	/**
	 * 	将long类型的seconds转为精确到小时的LocalDateTime
	 * @param seconds
	 * @return
	 */
	public static LocalDateTime parseSecondsToLocalDateTimeHourly(long seconds) {
        // 将秒级时间戳转换为毫秒级时间戳
        long timestampMillis = seconds * 1000L;
        // 使用Instant将毫秒级时间戳转换为LocalDateTime
        Instant instant = Instant.ofEpochMilli(timestampMillis);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        // 精确到小时
        LocalDateTime truncatedDateTime = localDateTime.withMinute(0).withSecond(0).withNano(0);
        return truncatedDateTime;
	}
	
	/**
	 * 	将LocaDate转为LocaDateTime
	 */
	public static LocalDateTime parseLocalDateToLocalDateTime(LocalDate localDate) {
		return localDate.atStartOfDay();
	}
	
	/**
	 * 	将LocaDateTime转为LocaDate
	 */
	public static LocalDate parseLocalDateTimeToLocalDate(LocalDateTime localDateTime) {
		return localDateTime.toLocalDate();
	}
	
	/**
	 * 	将long类型的milliseconds转为精确到小时的long类型seconds
	 * @param seconds
	 * @return
	 */
	public static long parseSecondsToSecondsHourly(long milliseconds) {
        // 将毫秒级时间戳转换为秒级时间戳
        long timestampSeconds = milliseconds / 1000L;
        // 使用Instant将秒级时间戳转换为LocalDateTime
        Instant instant = Instant.ofEpochSecond(timestampSeconds);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        // 精确到小时
        LocalDateTime truncatedDateTime = localDateTime.withMinute(0).withSecond(0).withNano(0);
        // 将精确到小时的LocalDateTime转换为秒级时间戳
        long timestampSecondsHourly = truncatedDateTime.toEpochSecond(ZoneOffset.ofHours(8));
        return timestampSecondsHourly;
	}
	
	
	/**
	 * 	将LocaDateTime转为Date
	 * @param localDate
	 * @return
	 */
	public static Date parseLocalDateTimeToDate(LocalDateTime localDateTime) {
		Instant instant = Instant.ofEpochSecond(localDateTime.toEpochSecond(ZoneOffset.ofHours(8)));
        return Date.from(instant);
	}
	
	/**
	 * 	将LocaDate转为Date
	 * @param localDate
	 * @return
	 */
	public static Date parseLocalDateToDate(LocalDate localDate) {
        Instant instant = localDate.atStartOfDay(ZoneOffset.ofHours(8)).toInstant();
        return Date.from(instant);
	}
	
	/**
	 * 	将Date转为LocaDateTime
	 * @param date
	 * @return
	 */
	public static LocalDateTime parseDateToLocalDateTime(Date date) {
		LocalDateTime localDateTime = date.toInstant().atOffset(ZoneOffset.ofHours(8)).toLocalDateTime();
        return localDateTime;
	}
	
	/**
	 * 	将Date转为LocaDate
	 * @param date
	 * @return
	 */
	public static LocalDate parseLocalDateToDate(Date date) {
		LocalDate localDate = date.toInstant().atOffset(ZoneOffset.ofHours(8)).toLocalDate();
        return localDate;
	}

	
	
	public static long getSecondsByMillis(Long timestamp) {
		if(Objects.isNull(timestamp)) {
			return 0l;
		}
		if(String.valueOf(timestamp).length() == 13) {
			return timestamp.longValue() / 1000;
		}
		return timestamp;
	}
	
	/**
	 * 	获取当前时间，精确到毫秒
	 * @return
	 */
	public static long getNowMilliseconds() {
		return LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
	}
	
	/**
	 * 	获取当前时间，精确到秒
	 * @return
	 */
	public static long getNowSeconds() {
		return LocalDateTime.now().toInstant(ZoneOffset.of("+8")).getEpochSecond();
	}

	/**
	 * 	将LocalDateTime转为long类型的milliseconds
	 * @param localDateTime
	 * @return
	 */
	public static long getMillisecondsByLocalDateTime(LocalDateTime localDateTime) {
	    ZoneId zone = ZoneId.systemDefault();
	    Instant instant = localDateTime.atZone(zone).toInstant();
	    return instant.toEpochMilli();
	}
	
	/**
	 * 	将LocalDateTime转为long类型的seconds
	 * @param localDateTime
	 * @return
	 */
	public static long getSecondsByLocalDateTime(LocalDateTime localDateTime) {
	    ZoneId zone = ZoneId.systemDefault();
	    Instant instant = localDateTime.atZone(zone).toInstant();
	    return instant.getEpochSecond();
	    //return localDateTime.toInstant(ZoneOffset.of("+8")).getEpochSecond();
	}
	
	/**
	 * 获取精确到当前小时的秒数
	 */
	public static long getNowSecondsHourly() {
		// 获取当前时间
        LocalDateTime now = LocalDateTime.now();
        // 将分钟和秒数设置为零
        LocalDateTime truncatedDateTime = now.withMinute(0).withSecond(0).withNano(0);
        // 计算精确到当前小时的秒数
        long seconds = truncatedDateTime.toInstant(ZoneOffset.of("+8")).getEpochSecond();
        return seconds;
	}
	
	
	
	/**
	 *	获取当前时间，根据指定格式化返回
	 * @param format
	 * @return
	 */
	public static String getNowDate(DateTimeFormatter formatter) {
		LocalDateTime localDateTime = LocalDateTime.now();
		return formatter.format(localDateTime);
	}
	
	/**
	 * 	获取今天开始时间，精确到秒
	 * @return
	 */
	public static long getToDayStartSeconds() {
		LocalDateTime localDateTime = LocalDate.now().atStartOfDay();
		ZoneId zone = ZoneId.systemDefault();
		Instant instant = localDateTime.atZone(zone).toInstant();
		return instant.getEpochSecond();
		/*return LocalDateTime.of(year, month, dayOfMonth, 0, 0, 0).toInstant(ZoneOffset.of("+8")).getEpochSecond();*/
	}
	
	/**
	 * 	获取今天开始时间，精确到毫秒
	 * @return
	 */
	public static long getToDayStartMilliseconds () {
		LocalDateTime localDateTime = LocalDate.now().atStartOfDay();
		ZoneId zone = ZoneId.systemDefault();
		Instant instant = localDateTime.atZone(zone).toInstant();
		return instant.toEpochMilli();
	}
	
	/**
	 * 	获取今天结束时间，精确到秒
	 * @return
	 */
	public static long getToDayEndSeconds() {
		LocalDateTime localDateTime = LocalDateTime.now();
		int year = localDateTime.getYear();
		Month month = localDateTime.getMonth();
		int dayOfMonth = localDateTime.getDayOfMonth();
		
		ZoneId zone = ZoneId.systemDefault();
		Instant instant = LocalDateTime.of(year, month, dayOfMonth, 23, 59, 59).atZone(zone).toInstant();
		return instant.getEpochSecond();
		/*return LocalDateTime.of(year, month, dayOfMonth, 23, 59, 59).toInstant(ZoneOffset.of("+8")).getEpochSecond();*/
	}
	
	
	/**
	 * 	获取今天结束时间，精确到毫秒
	 * @return
	 */
	public static long getToDayEndMilliseconds() {
		LocalDateTime localDateTime = LocalDateTime.now();
		int year = localDateTime.getYear();
		Month month = localDateTime.getMonth();
		int dayOfMonth = localDateTime.getDayOfMonth();
		
		ZoneId zone = ZoneId.systemDefault();
		Instant instant = LocalDateTime.of(year, month, dayOfMonth, 23, 59, 59).atZone(zone).toInstant();
		return instant.toEpochMilli();
	}
	
	/**
	 * 	获取指定日期的开始时间，精确到秒
	 * @return
	 */
	public static long getStartSecondsByLocalDate(LocalDate locaDate) {
		LocalDateTime localDateTime = locaDate.atStartOfDay();
		ZoneId zone = ZoneId.systemDefault();
		Instant instant = localDateTime.atZone(zone).toInstant();
		return instant.getEpochSecond();
		/*return LocalDateTime.of(year, month, dayOfMonth, 0, 0, 0).toInstant(ZoneOffset.of("+8")).getEpochSecond();*/
	}
	
	/**
	 * 	获取指定日期的结束时间，精确到秒
	 * @return
	 */
	public static long getEndSecondsByLocalDate(LocalDate locaDate) {
		LocalDateTime localDateTime = locaDate.atStartOfDay();
		int year = localDateTime.getYear();
		Month month = localDateTime.getMonth();
		int dayOfMonth = localDateTime.getDayOfMonth();
		
		ZoneId zone = ZoneId.systemDefault();
		Instant instant = LocalDateTime.of(year, month, dayOfMonth, 23, 59, 59).atZone(zone).toInstant();
		return instant.getEpochSecond();
		/*return LocalDateTime.of(year, month, dayOfMonth, 23, 59, 59).toInstant(ZoneOffset.of("+8")).getEpochSecond();*/
	}
	
	/**
	 * 	获取指定日期的开始时间，精确到秒
	 * @return
	 */
	public static long getStartSecondsByLocalDateTime(LocalDateTime localDateTime) {
		ZoneId zone = ZoneId.systemDefault();
		Instant instant = localDateTime.toLocalDate().atStartOfDay().atZone(zone).toInstant();
		return instant.getEpochSecond();
		/*return LocalDateTime.of(year, month, dayOfMonth, 0, 0, 0).toInstant(ZoneOffset.of("+8")).getEpochSecond();*/
	}
	
	/**
	 * 	获取指定日期的结束时间，精确到秒
	 * @return
	 */
	public static long getEndSecondsByLocalDateTime(LocalDateTime localDateTime) {
		int year = localDateTime.getYear();
		Month month = localDateTime.getMonth();
		int dayOfMonth = localDateTime.getDayOfMonth();
		
		ZoneId zone = ZoneId.systemDefault();
		Instant instant = LocalDateTime.of(year, month, dayOfMonth, 23, 59, 59).atZone(zone).toInstant();
		return instant.getEpochSecond();
		/*return LocalDateTime.of(year, month, dayOfMonth, 23, 59, 59).toInstant(ZoneOffset.of("+8")).getEpochSecond();*/
	}
	
	/**
	 * 	获取指定小时的开始时间，精确到秒
	 * @return
	 */
	public static long getStartSecondsOfHours(LocalDateTime localDateTime) {
		LocalDateTime startOfHour = localDateTime.withMinute(0).withSecond(0).withNano(0); // 将分钟、秒和纳秒设为零
		long seconds = startOfHour.toInstant(ZoneOffset.of("+8")).getEpochSecond();
		return seconds;
		/*return LocalDateTime.of(year, month, dayOfMonth, 0, 0, 0).toInstant(ZoneOffset.of("+8")).getEpochSecond();*/
	}
	
	/**
	 * 	获取指定小时的结果时间，精确到秒
	 * @return
	 */
	public static long getEndSecondsOfHours(LocalDateTime localDateTime) {
        LocalDateTime endOfHour = localDateTime.withMinute(59).withSecond(59).withNano(999999999); // 将分钟、秒和纳秒设为它们的最大值
		long seconds = endOfHour.toInstant(ZoneOffset.of("+8")).getEpochSecond();
		return seconds;
		/*return LocalDateTime.of(year, month, dayOfMonth, 0, 0, 0).toInstant(ZoneOffset.of("+8")).getEpochSecond();*/
	}
	
	/**
	 * 	获取指定日期的开始时间，精确到毫秒
	 * @return
	 */
	public static long getStartMillisecondsByLocaDate(LocalDate locaDate) {
		LocalDateTime localDateTime = locaDate.atStartOfDay();
		ZoneId zone = ZoneId.systemDefault();
		Instant instant = localDateTime.atZone(zone).toInstant();
		return instant.toEpochMilli();
	}
	
	/**
	 * 	获取今天结束时间，精确到毫秒
	 * @return
	 */
	public static long getEndMillisecondsByLocaDate(LocalDate locaDate) {
		LocalDateTime localDateTime = locaDate.atStartOfDay();
		int year = localDateTime.getYear();
		Month month = localDateTime.getMonth();
		int dayOfMonth = localDateTime.getDayOfMonth();
		
		ZoneId zone = ZoneId.systemDefault();
		Instant instant = LocalDateTime.of(year, month, dayOfMonth, 23, 59, 59).atZone(zone).toInstant();
		return instant.toEpochMilli();
	}
	
	/**
	 * 	获取指定日期的开始时间，精确到毫秒
	 * @return
	 */
	public static long getStartMillisecondsByLocaDate(long milliseconds) {
		LocalDate locaDate = parseMillisecondsToLocalDate(milliseconds);
		LocalDateTime localDateTime = locaDate.atStartOfDay();
		ZoneId zone = ZoneId.systemDefault();
		Instant instant = localDateTime.atZone(zone).toInstant();
		return instant.toEpochMilli();
		/*return LocalDateTime.of(year, month, dayOfMonth, 0, 0, 0).toInstant(ZoneOffset.of("+8")).getEpochSecond();*/
	}

	
	
	/**
	 * 获取月份中的第几天
	 * @return
	 */
	public static int getDayOfMonth() {
		LocalDate today = LocalDate.now();
		int dayOfMonth = today.getDayOfMonth();
		return dayOfMonth;
	}
	
	/**
	 * 获取一周中的第几天
	 * @return
	 */
	public static int getDayOfWeek() {
		LocalDate today = LocalDate.now();
		DayOfWeek dayOfWeek = today.getDayOfWeek();
		return dayOfWeek.getValue();
	}
	
	/**
	 * 获取当前月份的总天数
	 * @return
	 */
	public static int getLengthOfMonth() {
		LocalDate today = LocalDate.now();
		int length = today.lengthOfMonth();
		return length;
	}
	
	/**
	 * 获取指定日期月份的总天数
	 * @return
	 */
	public static int getLengthOfMonth(LocalDate localDate) {
		return localDate.lengthOfMonth();
	}
	
	/**
	 * 是否为闰年
	 * @return
	 */
	public static boolean isLeapYear() {
		LocalDate today = LocalDate.now();
		boolean leapYear = today.isLeapYear();
		return leapYear;
	}
	
	/**
	 * 判断两个日期是否相等
	 * @param anyLocalDate
	 * @param anyLocalDate2
	 */
	public static boolean equalsLocalDate(LocalDate anyLocalDate, LocalDate anyLocalDate2) {
		return anyLocalDate.equals(anyLocalDate2);
	}
	
	/**
	 * 判断任意日期是否与今天相等
	 * @param anyLocalDate
	 */
	public static boolean equalsLocalDate(LocalDate anyLocalDate) {
		LocalDate today = LocalDate.now();
		return today.equals(anyLocalDate);
	}
	
	/**
	 * 以当前日期往前推指定天数
	 * @param daysToSubtract
	 * @return
	 */
	public static LocalDate minusDays(long daysToSubtract) {
		return LocalDate.now().minusDays(daysToSubtract);
	}
	
	
	/**
	 * 以当前日期往前推指定天数
	 * @param daysToSubtract
	 * @return
	 */
	public static LocalDateTime minusDaysGetLocalDateTime(long daysToSubtract) {
		return LocalDateTime.now().minusDays(daysToSubtract);
	}

	/**
	 * 以当前时间往前推指定小时数
	 * @param hours
	 * @return
	 */
	public static LocalDateTime minusHoursGetLocalDateTime(long hours) {
		return LocalDateTime.now().minusHours(hours);
	}
	
	/**
	 * 以当前时间往前推指定分钟数
	 * @param daysToSubtract
	 * @return
	 */
	public static long minusMinutes(long minutes) {
		return LocalDateTime.now().minusMinutes(minutes).toInstant(ZoneOffset.of("+8")).getEpochSecond();
	}
	
	/**
	 * 以当前时间往前推指定小时
	 * @param daysToSubtract
	 * @return
	 */
	public static long minusHours(long hours) {
		return LocalDateTime.now().minusHours(hours).toInstant(ZoneOffset.of("+8")).getEpochSecond();
	}
	
	/**
	 * 以当前日期往后推指定天数
	 * @param daysToAdd
	 * @return
	 */
	public static LocalDate plusDays(long daysToAdd) {
		return LocalDate.now().plusDays(daysToAdd);
	}
	
	/**
	 * 以当前日期往后推指定天数
	 * @param daysToAdd
	 * @return
	 */
	public static LocalDateTime plusDaysGetLocalDateTime(long daysToAdd) {
		return LocalDateTime.now().plusDays(daysToAdd);
	}
	
	/**
	 * 获取当天所在月的第一天日期
	 * @return
	 */
	public static LocalDate getFirstDayOfMonth() {
		return LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
	}
	
	/**
	 * 获取当天所在月的最后一天日期
	 * @return
	 */
	public static LocalDate getLastDayOfMonth() {
		return LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
	}
	
	/**
	 * 获取前几个月的第一天日期
	 * @return
	 */
	public static LocalDate getPreviousFirstDayOfMonth(long monthsToSubtract) {
		LocalDate localDate = LocalDate.now();
		localDate = localDate.minusMonths(monthsToSubtract);
		return localDate.with(TemporalAdjusters.firstDayOfMonth());
	}
	
	/**
	 * 获取前几个月的最后一天日期
	 * @return
	 */
	public static LocalDate getPreviousLastDayOfMonth(long monthsToSubtract) {
		LocalDate localDate = LocalDate.now();
		localDate = localDate.minusMonths(monthsToSubtract);
		return localDate.with(TemporalAdjusters.lastDayOfMonth());
	}
	
	/**
	 * 获取今天对应上个月同一天的日期
	 * 本月的当天日期对应的天数大于上个月的总天数时，取上个月的最后一天
	 * 本月的最后一天日期对应的天数小于上月的总天数时，取上个月的最后一天
	 * @return
	 */
	public static LocalDate getPreviousMonthSameDay() {
		// 获取今天的日期
        LocalDate today = LocalDate.now();
        // 计算上个月的日期
        LocalDate lastMonth = today.minusMonths(1);
        // 获取本月和上个月的天数
        int currentMonthDays = today.lengthOfMonth();
        int lastMonthDays = lastMonth.lengthOfMonth();
        // 调整上个月的日期
        if (today.getDayOfMonth() > lastMonthDays) {
            // 本月的当天日期对应的天数大于上个月的总天数时，取上个月的最后一天
            lastMonth = lastMonth.withDayOfMonth(lastMonthDays);
        } else if (today.withDayOfMonth(currentMonthDays).getDayOfMonth() < lastMonthDays) {
            // 本月的最后一天日期对应的天数小于上月的总天数时，取上个月的最后一天
            lastMonth = lastMonth.withDayOfMonth(lastMonthDays);
        }
        return lastMonth;
	}
	
	/**
	 * 获取指定日期对应上个月同一天的日期
	 * 本月的当天日期对应的天数大于上个月的总天数时，取上个月的最后一天
	 * 本月的最后一天日期对应的天数小于上月的总天数时，取上个月的最后一天
	 * @return
	 */
	public static LocalDate getPreviousMonthSameDay(LocalDate anyLocalDate) {
		
		//LocalDate anyLocalDate = LocalDate.of(2023, 11, 30);
		
		// 计算上个月的日期
		LocalDate lastMonth = anyLocalDate.minusMonths(1);
		// 获取本月和上个月的天数
		int currentMonthDays = anyLocalDate.lengthOfMonth();
		int lastMonthDays = lastMonth.lengthOfMonth();
		// 调整上个月的日期
		if (anyLocalDate.getDayOfMonth() > lastMonthDays) {
			// 本月的当天日期对应的天数大于上个月的总天数时，取上个月的最后一天
			lastMonth = lastMonth.withDayOfMonth(lastMonthDays);
		} else if (anyLocalDate.withDayOfMonth(currentMonthDays).getDayOfMonth() < lastMonthDays) {
			// 本月的最后一天日期对应的天数小于上月的总天数时，取上个月的最后一天
			lastMonth = lastMonth.withDayOfMonth(lastMonthDays);
		}
		return lastMonth;
	}
	
	/**
	 * 获取日期 from 与 日期 to 的区间天数，如：
	 * from = 2020-04-18 00:00:00 to=2020-04-19 00:00:00 返回 1
	 * from = 2020-04-19 00:00:00 to=2020-04-18 00:00:00 返回 -1
	 * @param from
	 * @param to
	 * @return
	 */
	public static long getDurationDay(LocalDateTime from, LocalDateTime to) {
		from = from.toLocalDate().atStartOfDay();
		to = to.toLocalDate().atStartOfDay();
		Duration duration = Duration.between(from, to);
		return duration.toDays();
		/* 
		 总天数:	duration.toDays();
		小时数: 	duration.toHours();
		分钟数: 	duration.toMinutes();
		秒数:		duration.getSeconds();
		毫秒数:	duration.toMillis();
		纳秒数: 	duration.toNanos();
		*/
	}
	
	/**
	 * 获取日期 from 与 日期 to 的区间天数，如：
	 * from = 2020-04-18 00:00:00 to=2020-04-19 00:00:00 返回 1
	 * from = 2020-04-19 00:00:00 to=2020-04-18 00:00:00 返回 -1
	 * @param from
	 * @param to
	 * @return
	 */
	public static Long getDurationDay(LocalDate from, LocalDate to) {
	 	LocalDateTime fromDateTime = from.atStartOfDay();
	    LocalDateTime toDateTime = to.atStartOfDay();
		Duration duration = Duration.between(fromDateTime, toDateTime);
		return duration.toDays();
	}
	
	/**
	 * 获取日期 from 与 日期 to 的区间天数，如：
	 * from = 2020-04-18 00:00:00 to=2020-04-19 00:00:00 返回 1
	 * from = 2020-04-19 00:00:00 to=2020-04-18 00:00:00 返回 -1
	 * @param from
	 * @param to
	 * @return
	 */
	public static Long getDurationDay(long fromSeconds, long toSeconds) {
		try {
			LocalDateTime fromLocalDateTime = parseSecondsToLocalDateTime(fromSeconds);
			LocalDateTime toLocalDateTime = parseSecondsToLocalDateTime(toSeconds);
			LocalDateTime from = fromLocalDateTime.toLocalDate().atStartOfDay();
			LocalDateTime to = toLocalDateTime.toLocalDate().atStartOfDay();
			Duration duration = Duration.between(from, to);
			return duration.toDays();
		} catch (Exception e) {
			return null;
		}
	}
	
	
	public static void main(String[] args) {
		
		LocalDate lowerLocalDate = LocalDateUtils.parseSecondsToLocalDate(1697614260);
        LocalDate upperLocalDate =  LocalDateUtils.parseSecondsToLocalDate(1697787060);
        
		long day = LocalDateUtils.getDurationDay(lowerLocalDate, upperLocalDate);
		System.out.println(day);
		
		
		// 创建一个 LocalDate 对象
        LocalDate localDates = LocalDate.of(2023, 10, 18);

        // 使用 atStartOfDay 方法将 LocalDate 转换为 LocalDateTime
        LocalDateTime localDateTimes = localDates.atStartOfDay();

        System.out.println("LocalDate: " + localDates);
        System.out.println("LocalDateTime: " + localDateTimes);
		
		
		Duration duration1 = Duration.of(7, ChronoUnit.DAYS);
		System.out.println(duration1.toDays());
		
		LocalDate firstDate = getPreviousFirstDayOfMonth(1);
		LocalDate endDate = getPreviousLastDayOfMonth(1);
		int length = firstDate.lengthOfMonth();
		List<LocalDate> dateList = new ArrayList<LocalDate>();
		System.out.println(length);
		for (int i = 0; i < length; i++) {
			LocalDate localDate = firstDate.plusDays(i);
			dateList.add(localDate);
		}
		
		for (LocalDate localDate : dateList) {
			System.out.println(formatLocalDateToString(localDate, DATE_PLAIN_FORMATTER));
		}
		System.out.println("-----------------------------");
		System.out.println(formatLocalDateToString(firstDate, DATE_PLAIN_FORMATTER));
		System.out.println(formatLocalDateToString(endDate, DATE_PLAIN_FORMATTER));
		
	}

	
	


	/**
	 * 	将时间字符串转为自定义时间格式的LocalDateTime
	 * @param dateStr
	 * @param format
	 * @return
	 */
	public static LocalDateTime formatStringToLocalDateTime(String dateStr, DateTimeFormatter formatter) {
	    return LocalDateTime.parse(dateStr, formatter);
	}
	
	/**
	 * 	将时间字符串转为自定义时间格式的LocalDate
	 * @param dateStr
	 * @param format
	 * @return
	 */
	public static LocalDate formatStringToLocalDate(String dateStr, DateTimeFormatter formatter) {
	    return LocalDate.parse(dateStr, formatter);
	}
	
	/**
	 * 将long类型的seconds转为自定义格式的字符串日期
	 * @param seconds 	long类型时间，精确到秒级
	 * @param format	返回的字符串日期格式
	 * @return
	 */
	public static String formatSecondsToString(long seconds, DateTimeFormatter formatter) {
	    Instant instant = Instant.ofEpochSecond(seconds);
//	    ZoneId zone = ZoneId.systemDefault();
//	    LocalDateTime localDateTime = instant.atZone(zone).toLocalDateTime();
	    LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
	    return formatter.format(localDateTime);
	}

	
	/**
	 * 将long类型的milli转为自定义格式的字符串日期
	 * @param seconds 	long类型时间，精确到毫秒级
	 * @param format	返回的字符串日期格式
	 * @return
	 */
	public static String formatMillisToString(long milli, DateTimeFormatter formatter) {
		Instant instant = Instant.ofEpochMilli(milli);
		ZoneId zone = ZoneId.systemDefault();
		LocalDate localDate =  instant.atZone(zone).toLocalDate();
		return formatter.format(localDate);
	}
	
	/**
	 * 	将LocalDateTime转为字符串格式
	 * @param localDateTime 带时分秒
	 * @param format
	 * @return
	 */
	public static String formatLocalDateTimeToString(LocalDateTime localDateTime, DateTimeFormatter formatter) {
		return formatter.format(localDateTime);
	}
	
	/**
	 * 	将LocalDateTime转为字符串格式
	 * @param localDate 
	 * @param format
	 * @return
	 */
	public static String formatLocalDateTimeToString(LocalDateTime localDateTime, String format) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
		return formatter.format(localDateTime);
	}
	
	
	/**
	 * 	将LocalDate转为字符串格式
	 * @param localDate 
	 * @param format
	 * @return
	 */
	public static String formatLocalDateToString(LocalDate localDate, DateTimeFormatter formatter) {
		return formatter.format(localDate);
	}
	
	/**
	 * 	将LocalDate转为字符串格式
	 * @param localDate 
	 * @param format
	 * @return
	 */
	public static String formatLocalDateToString(LocalDate localDate, String format) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
		return formatter.format(localDate);
	}
	
	
	
	
	
}
