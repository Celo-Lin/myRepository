package com.juan.adx.task.config.database;

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

import com.juan.adx.task.config.database.alg.table.DateTimeTablePreciseShardingAlgorithm;
import com.juan.adx.task.config.database.alg.table.DateTimeTableRangeShardingAlgorithm;
import com.juan.adx.task.config.database.enums.DatabaseSharKey;
import com.juan.adx.task.mapper.bidrecord.BidRecordMapper;

@Configuration
public class BidRecordShardingJdbcConfiguration {
	
	
    @Autowired
    @Qualifier("bidRecordDataSource")
    private DataSource bidRecordDataSource;
    
    
    @Primary
    @Bean(name = "shardingBidRecordDataSource")
    public DataSource shardingBidRecordDataSource() throws SQLException {
        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
        shardingRuleConfig.getTableRuleConfigs().addAll(getTableRuleConfiguration());
        shardingRuleConfig.getBindingTableGroups().addAll(getAllTables());
        shardingRuleConfig.setDefaultTableShardingStrategyConfig(new NoneShardingStrategyConfiguration());
        Properties properties = new Properties();
        properties.setProperty("sql.show", Boolean.FALSE.toString());
        return ShardingDataSourceFactory.createDataSource(createDataSourceMap(), shardingRuleConfig, properties);
    }


    private List<TableRuleConfiguration> getTableRuleConfiguration() {
    	List<TableRuleConfiguration> configurations = new ArrayList<TableRuleConfiguration>();
    	//为按日期分表的表添加策略
        List<String> shardingDayTables = getShardingByDateTimeTables();
        if (shardingDayTables != null && !shardingDayTables.isEmpty()) {
            for (String table : shardingDayTables) {
                TableRuleConfiguration configuration = new TableRuleConfiguration(table,
                        "bidRecordDatabase." + table + "_${2023..2099}${['01','02','03','04','05','06','07','08','09','10','11','12']}${['01','02','03','04','05','06','07','08','09','10','11','12','13','14','15','16','17','18','19','20','21','22','23','24','25','26','27','28','29','30','31']}");
                configuration.setTableShardingStrategyConfig(new StandardShardingStrategyConfiguration(DatabaseSharKey.SHAR_DATE_TIME.getName(), new DateTimeTablePreciseShardingAlgorithm(), new DateTimeTableRangeShardingAlgorithm()));
                configurations.add(configuration);
            }
        }
        
        //为不需要分表的表添加策略
        List<String> notShardingTables = getNotShardingTables();
        if (notShardingTables != null && !notShardingTables.isEmpty()) {
            for (String table : notShardingTables) {
                TableRuleConfiguration configuration = new TableRuleConfiguration(table, "bidRecordDatabase." + table);
                configurations.add(configuration);
            }
        }
        return configurations;
    }
    
    
    private Map<String, DataSource> createDataSourceMap(){
    	Map<String, DataSource> dataSourceMap = new HashMap<String, DataSource>();
    	dataSourceMap.put("bidRecordDatabase", bidRecordDataSource);
        return dataSourceMap;
    }

    /**
     * 获取所有表名集合
     * 无需分库分表的不要在此配置
     *
     * @return
     */
    private List<String> getAllTables() {
        List<String> tables = new ArrayList<>();
        tables.addAll(getNotShardingTables());
        tables.addAll(getShardingByDateTimeTables());
        tables.addAll(getShardingByModTables());
        return tables;
    }

    /**
     * 获取分库不分表的表
     *
     * @return
     */
    private List<String> getNotShardingTables() {
        List<String> tables = new ArrayList<String>();
        return tables;
    }

    /**
     * 获取要按日期分表的表名集合
     *
     * @return
     */
    private List<String> getShardingByDateTimeTables() {
        List<String> tables = new ArrayList<String>();
        tables.add(BidRecordMapper.TABLE_ADX_AD_BID_RECORD);
        
        
        return tables;
    }

    /**
     * 获取按取模分库分表的表名,单独按取模分表的,统一使用 SHAR_ID 字段作为分表字段
     *
     * @return
     */
    private List<String> getShardingByModTables() {
        List<String> tables = new ArrayList<String>();
        return tables;
    }
    
}
