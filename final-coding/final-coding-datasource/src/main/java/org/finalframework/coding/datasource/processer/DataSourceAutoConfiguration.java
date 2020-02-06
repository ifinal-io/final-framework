package org.finalframework.coding.datasource.processer;


import org.finalframework.coding.annotation.Template;
import org.finalframework.coding.file.JavaSource;

import java.util.ArrayList;
import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2020-01-16 14:45:10
 * @since 1.0
 */

@Template("datasource/dataSourceAutoConfiguration.vm")
public class DataSourceAutoConfiguration implements JavaSource {

    private final String packageName;
    private final String simpleName;
    private final String name;
    private final String prefix;
    private final List<String> dataSources = new ArrayList<>();
    private String dataSource;
    private ShardingRuleConfiguration shardingRule;

    private MapperScanConfiguration mapperScan;

    public DataSourceAutoConfiguration(String prefix, String packageName, String simpleName) {
        this.prefix = prefix;
        this.packageName = packageName;
        this.simpleName = simpleName;
        this.name = packageName + "." + simpleName;
    }

    @Override
    public String getPackageName() {
        return packageName;
    }

    @Override
    public String getSimpleName() {
        return simpleName;
    }

    @Override
    public String getName() {
        return name;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public List<String> getDataSources() {
        return dataSources;
    }

    public ShardingRuleConfiguration getShardingRule() {
        return shardingRule;
    }

    public void setShardingRule(ShardingRuleConfiguration shardingRule) {
        this.shardingRule = shardingRule;
    }

    public MapperScanConfiguration getMapperScan() {
        return mapperScan;
    }

    public void setMapperScan(MapperScanConfiguration mapperScan) {
        this.mapperScan = mapperScan;
    }
}

