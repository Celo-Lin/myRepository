package com.juan.adx.task.config.database.alg.table;

import java.util.Collection;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

/**
 * 	按月分片，用于处理使用单一键作为分片键的=与IN进行分片的场景
 */
public class MonthPreciseTableShardingAlgorithm implements PreciseShardingAlgorithm<String> {

    /**
     * 	等值查询分表路由策略, 根据传入date返回响应以年月结尾的表
     * @param availableTargetNames 可用表名
     * @param shardingValue 分片条件
     * @return 符合分片条件的表名
     */
	@Override
	public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<String> shardingValue) {
		// 根据配置的分表规则生成目标表的后缀
        String tableSuffix = shardingValue.getValue();
        for (String availableTableName : availableTargetNames) {
            if (availableTableName.endsWith(tableSuffix)) {
                return availableTableName;
            }
        }
        return null;
	}
	
}