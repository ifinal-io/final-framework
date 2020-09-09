

package org.finalframework.spiriter.jdbc.api.controller;

import org.finalframework.spiriter.jdbc.model.ResultSets;
import org.finalframework.spiriter.jdbc.service.DataSourceService;
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
 * @author likly
 * @version 1.0
 * @date 2020-05-19 15:53:36
 * @since 1.0
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

