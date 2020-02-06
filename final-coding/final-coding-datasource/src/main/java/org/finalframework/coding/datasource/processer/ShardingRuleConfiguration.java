package org.finalframework.coding.datasource.processer;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2020-02-05 11:53:09
 * @since 1.0
 */
public class ShardingRuleConfiguration implements Serializable {
    private List<String> tables = new ArrayList<>();

    public List<String> getTables() {
        return tables;
    }

    public void setTables(List<String> tables) {
        this.tables = tables;
    }
}

