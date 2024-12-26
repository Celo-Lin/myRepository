package com.juan.adx.manage.config.database;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.NoneShardingStrategyConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.StandardShardingStrategyConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.juan.adx.manage.config.alg.MonthPreciseTableShardingAlgorithm;
import com.juan.adx.manage.config.alg.MonthRangeTableShardingAlgorithm;

@Configuration
public class AdxShardingJdbcConfiguration {
	
	
    @Autowired
    @Qualifier("adxDataSource")
    private DataSource adxDataSource;
    
    
    @Primary
    @Bean(name = "shardingDspDataSource")
    public DataSource shardingDspDataSource() throws SQLException {
        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
        shardingRuleConfig.getTableRuleConfigs().addAll(getTableRuleConfiguration());
        shardingRuleConfig.getBindingTableGroups().addAll(getShardingTables());
        shardingRuleConfig.setDefaultTableShardingStrategyConfig(new NoneShardingStrategyConfiguration());
        Properties properties = new Properties();
        properties.setProperty("sql.show", Boolean.FALSE.toString());
        return ShardingDataSourceFactory.createDataSource(createDataSourceMap(), shardingRuleConfig, properties);
    }


    private List<TableRuleConfiguration> getTableRuleConfiguration() {
    	List<TableRuleConfiguration> configurations = new ArrayList<TableRuleConfiguration>();
    	//为按日期分表的表添加策略
        List<String> shardingDayTables = getShardingByDayTables();
        if (shardingDayTables != null && !shardingDayTables.isEmpty()) {
            for (String table : shardingDayTables) {
                TableRuleConfiguration configuration = new TableRuleConfiguration(table,
                        "adxDataSource." + table + "_${2019..2099}${['01','02','03','04','05','06','07','08','09','10','11','12']}");
                configuration.setTableShardingStrategyConfig(new StandardShardingStrategyConfiguration("FSHAR_MONTH", new MonthPreciseTableShardingAlgorithm(), new MonthRangeTableShardingAlgorithm()));
                configurations.add(configuration);
            }
        }
        return configurations;
    }
    
    private List<String> getShardingByDayTables() {
        List<String> tables = new ArrayList<String>();
        tables.add("DSP_ACTIVE_LOG");
        return tables;
    }
    
    private Map<String, DataSource> createDataSourceMap(){
    	Map<String, DataSource> dataSourceMap = new HashMap<String, DataSource>();
    	dataSourceMap.put("adxDataSource", adxDataSource);
        return dataSourceMap;
    }
    
    /**
     * 	获取所有表名集合，无需分库分表的不要在此配置
     *
     * @return
     */
    private List<String> getShardingTables(){
    	List<String> tables = new ArrayList<String>();
    	tables.addAll(getShardingByDayTables());
    	return tables;
    }
    
}
