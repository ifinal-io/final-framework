package org.ifinal.finalframework.spiriter.jdbc.api.controller;

import org.apache.ibatis.session.SqlSessionFactory;
import org.ifinal.finalframework.spiriter.jdbc.dao.mapper.CommonMapper;
import org.ifinal.finalframework.spiriter.jdbc.model.Table;
import org.ifinal.finalframework.spiriter.jdbc.service.DataSourceService;
import org.ifinal.finalframework.spiriter.jdbc.service.DatabaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.List;


/**
 * @author likly
 * @version 1.0.0
 *
 * @since 1.0.0
 */
@RestController
@RequestMapping("/api/jdbc")
public class DatabaseApiController implements InitializingBean {
    public static final Logger logger = LoggerFactory.getLogger(DatabaseApiController.class);

    @Resource
    private DatabaseService databaseService;
    @Resource
    private DataSourceService dataSourceService;
    @Resource
    private SqlSessionFactory sqlSessionFactory;

    private CommonMapper commonMapper;

    @GetMapping("menus")
    public List<Table> tables(String dataSource) throws SQLException {
        return databaseService.showTables(dataSourceService.getDataSource(dataSource));
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        this.commonMapper = sqlSessionFactory.getConfiguration().getMapper(CommonMapper.class, sqlSessionFactory.openSession());
    }
}

