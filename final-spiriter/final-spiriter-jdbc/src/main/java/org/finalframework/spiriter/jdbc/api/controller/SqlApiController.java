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

package org.ifinalframework.spiriter.jdbc.api.controller;

import org.ifinalframework.spiriter.jdbc.model.ResultSets;
import org.ifinalframework.spiriter.jdbc.service.DataSourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


/**
 * @author ilikly
 * @version 1.0.0
 *
 * @since 1.0.0
 */
@RestController
@RequestMapping("/api/jdbc")
public class SqlApiController {
    public static final Logger logger = LoggerFactory.getLogger(SqlApiController.class);

    @Resource
    private DataSourceService dataSourceService;

    @PostMapping("/query")
    public Object query(@RequestParam(value = "datasource", required = false, defaultValue = "dataSource") String datasource, String sql) throws SQLException {
        return ResultSets.from(dataSourceService.getDataSource(datasource).getConnection().createStatement().executeQuery(sql));
    }

    @PostMapping("/execute")
    public List<ResultSets> execute(@RequestParam(value = "datasource", required = false, defaultValue = "dataSource") String datasource, String sql) throws SQLException {
        final DataSource dataSource = dataSourceService.getDataSource(datasource);
        try (final Connection connection = dataSource.getConnection()) {
            final Statement statement = connection.createStatement();
//        statement.addBatch(sql);
//        statement.addBatch("show  tables");
//        statement.addBatch("select * from person");
//        final int[] ints = statement.executeBatch();
            statement.execute(sql);
            List<ResultSets> result = new ArrayList<>();
            do {
                result.add(ResultSets.from(statement.getResultSet()));
            } while (statement.getMoreResults());
            return result;
        }

    }


}

