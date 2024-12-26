package com.juan.adx.task.config.database.alg.table;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;

import com.google.common.collect.Range;
import com.juan.adx.common.utils.LocalDateUtils;

/**
 * 
 *	Between的分表算法实现
 *
 */
public final class DateTimeTableRangeShardingAlgorithm implements RangeShardingAlgorithm<Long> {

    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, RangeShardingValue<Long> rangeShardingValue) {
        Collection<String> collect = new ArrayList<>();
        
        Range<Long> valueRange = rangeShardingValue.getValueRange();
        
        Long lowerValue = valueRange.lowerEndpoint();
        Long upperValue = valueRange.upperEndpoint();
        
        LocalDateTime lowerLocalDate = LocalDateUtils.parseSecondsToLocalDateTime(lowerValue);
        LocalDateTime upperLocalDate =  LocalDateUtils.parseSecondsToLocalDateTime(upperValue);
        
        //LocalDate lowerLocalDate = LocalDateUtils.formatStringToLocalDate(lowerValue, LocalDateUtils.DATE_TIME_FORMATTER);
        //LocalDate upperLocalDate = LocalDateUtils.formatStringToLocalDate(upperValue, LocalDateUtils.DATE_TIME_FORMATTER);
        
        long durationDay = LocalDateUtils.getDurationDay(lowerLocalDate, upperLocalDate);
        
        if(durationDay == 0) {
        	String suffix = LocalDateUtils.formatLocalDateTimeToString(upperLocalDate, LocalDateUtils.DATE_PLAIN_FORMATTER);
        	for (String targetName : availableTargetNames) {
                if (targetName.endsWith(suffix)) {
                	collect.add(targetName);
                	break;
                }
            }
        	return collect;
        }
        
		/*String lowerSuffix = LocalDateUtils.formatLocalDateTimeToString(lowerLocalDate, LocalDateUtils.DATE_PLAIN_FORMATTER);
		String upperSuffix = LocalDateUtils.formatLocalDateTimeToString(upperLocalDate, LocalDateUtils.DATE_PLAIN_FORMATTER);
		int lowerEndpoint = Integer.parseInt(lowerSuffix);
		int upperEndpoint = Integer.parseInt(upperSuffix);
		for (long i = lowerEndpoint; i <= upperEndpoint; i++) {
		    for (String targetName : availableTargetNames) {
		        if (targetName.endsWith(String.valueOf(i))) {
		            collect.add(targetName);
		        }
		    }
		}*/
        
        for (long i = 0; i <= durationDay; i++) {
        	LocalDateTime localDateTime = upperLocalDate.minusDays(i);
        	String datePlain = LocalDateUtils.formatLocalDateTimeToString(localDateTime, LocalDateUtils.DATE_PLAIN_FORMATTER);
        	for (String targetName : availableTargetNames) {
                if ( targetName.endsWith(datePlain) ) {
                    collect.add(targetName);
                }
            }
        }
        return collect;
    }
    
}