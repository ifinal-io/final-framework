

package org.finalframework.spiriter.jdbc.service.impl;

import org.apache.ibatis.session.SqlSessionFactory;
import org.finalframework.spiriter.jdbc.dao.mapper.CommonMapper;
import org.finalframework.spiriter.jdbc.model.Table;
import org.finalframework.spiriter.jdbc.service.DatabaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


/**
 * @author likly
 * @version 1.0
 * @date 2020-05-19 12:51:00
 * @since 1.0
 */
@Service
public class DatabaseServiceImpl implements DatabaseService, InitializingBean {
    public static final Logger logger = LoggerFactory.getLogger(DatabaseServiceImpl.class);

    private static final String ACTUAL_SEPARATOR = "_";
    private static final Pattern PATTERN = Pattern.compile("_\\d+$");

    @Resource
    private SqlSessionFactory sqlSessionFactory;

    private CommonMapper commonMapper;

    @Override
    public List<Table> showTables(DataSource dataSource) throws SQLException {
        final Map<String, Set<String>> shardingTables = new HashMap<>();

        final List<String> tables = commonMapper.showTables();

        tables.forEach(table -> {
            if (PATTERN.matcher(table).find()) {
                final String logicTable = table.substring(0, table.lastIndexOf(ACTUAL_SEPARATOR));
                final Set<String> actualTables = shardingTables.computeIfAbsent(logicTable, key -> new HashSet<>());
                actualTables.add(table);
            } else {
                final Set<String> actualTables = shardingTables.computeIfAbsent(table, key -> new HashSet<>());
                actualTables.add(table);
            }
        });

        return shardingTables.entrySet().stream()
                .map(entry -> {
                    final Table result = new Table();
                    result.setLogicTable(entry.getKey());
                    result.setActualTables(entry.getValue().stream().sorted().collect(Collectors.toList()));
                    return result;
                }).collect(Collectors.toList());

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.commonMapper = sqlSessionFactory.getConfiguration().getMapper(CommonMapper.class, sqlSessionFactory.openSession());
    }
}

