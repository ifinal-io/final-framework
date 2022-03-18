/*
 * Copyright 2020-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ifinalframework.spiriter.jdbc.service.impl;

import org.apache.ibatis.session.SqlSessionFactory;
import org.ifinalframework.spiriter.jdbc.dao.mapper.CommonMapper;
import org.ifinalframework.spiriter.jdbc.model.Table;
import org.ifinalframework.spiriter.jdbc.service.DatabaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


/**
 * @author ilikly
 * @version 1.0.0
 * @since 1.0.0
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

