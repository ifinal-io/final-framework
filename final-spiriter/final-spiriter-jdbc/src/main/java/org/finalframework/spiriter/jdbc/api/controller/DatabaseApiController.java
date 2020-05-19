package org.finalframework.spiriter.jdbc.api.controller;

import org.finalframework.spiriter.jdbc.model.Table;
import org.finalframework.spiriter.jdbc.service.DataSourceService;
import org.finalframework.spiriter.jdbc.service.DatabaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;


/**
 * @author likly
 * @version 1.0
 * @date 2020-05-19 12:55:46
 * @since 1.0
 */
@RestController
@RequestMapping("/api/jdbc")
public class DatabaseApiController {
    public static final Logger logger = LoggerFactory.getLogger(DatabaseApiController.class);

    @Resource
    private DatabaseService databaseService;
    @Resource
    private DataSourceService dataSourceService;

    @GetMapping("tables")
    public List<Table> tables(String dataSource) throws SQLException {
        return databaseService.showTables(dataSourceService.getDataSource(dataSource));
    }


}

