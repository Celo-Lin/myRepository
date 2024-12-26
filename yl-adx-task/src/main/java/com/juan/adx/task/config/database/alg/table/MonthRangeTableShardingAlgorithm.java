package com.juan.adx.task.config.database.alg.table;

import java.util.Collection;
import java.util.LinkedHashSet;

import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;

import com.google.common.collect.Range;

/**
 * 	按月分片，用于处理使用单一键作为分片键的BETWEEN AND、>、<、>=、<=进行分片的场景
 *
 */
public class MonthRangeTableShardingAlgorithm implements RangeShardingAlgorithm<String> {

	/**
     * 	范围查询分表路由策略, 根据传入date返回响应以年月结尾的表
     * @param availableTargetNames 可用表名
     * @param shardingValue 分片条件
     * @return 符合分片条件的表名
     */
	@Override
	public Collection<String> doSharding(Collection<String> availableTargetNames, RangeShardingValue<String> shardingValue) {

		Collection<String> result = new LinkedHashSet<String>();
        Range<String> shardingKey = shardingValue.getValueRange();
        //获取开始的年月
        Integer startTime  = Integer.valueOf(shardingKey.lowerEndpoint());
        //获取结束的年月
        Integer endTime = Integer.valueOf(shardingKey.upperEndpoint());
        int start = startTime;
        while(startTime.intValue() <= endTime.intValue()){
            StringBuffer tableName = new StringBuffer();
            tableName.append(shardingValue.getLogicTableName()).append("_").append(start);
            result.add(tableName.toString());
            start++;
        }
        return result;
	}

}
