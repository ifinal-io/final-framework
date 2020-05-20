package org.finalframework.spiriter.mysql.api.controller;

import org.finalframework.spiriter.mysql.model.ResultSets;
import org.finalframework.spiriter.mysql.service.DataSourceService;
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

    private ResultSet getFirstResult(Statement stmt) throws SQLException {
        ResultSet rs = stmt.getResultSet();
        while (rs == null) {
            // move forward to get the first resultset in case the driver
            // doesn't return the resultset as the first result (HSQLDB 2.1)
            if (stmt.getMoreResults()) {
                rs = stmt.getResultSet();
            } else {
                if (stmt.getUpdateCount() == -1) {
                    // no more results. Must be no resultset
                    break;
                }
            }
        }
        return rs;
    }

    private ResultSet getNextResultSet(Statement stmt) throws SQLException {
        // Making this method tolerant of bad JDBC drivers
        try {
            if (stmt.getConnection().getMetaData().supportsMultipleResultSets()) {
                // Crazy Standard JDBC way of determining if there are more results
                if (!(!stmt.getMoreResults() && stmt.getUpdateCount() == -1)) {
                    ResultSet rs = stmt.getResultSet();
                    if (rs == null) {
                        return getNextResultSet(stmt);
                    } else {
                        return rs;
                    }
                }
            }
        } catch (Exception e) {
            // Intentionally ignored.
        }
        return null;
    }

}

