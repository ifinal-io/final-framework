package org.finalframework.test.controller;

import org.apache.ibatis.session.SqlSessionFactory;
import org.finalframework.spiriter.jdbc.model.ResultSets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * @author likly
 * @version 1.0
 * @date 2020-05-19 10:45:49
 * @since 1.0
 */
@RestController
@RequestMapping("/sql")
public class SqlController {
    public static final Logger logger = LoggerFactory.getLogger(SqlController.class);

    @Resource
    private SqlSessionFactory sqlSessionFactory;


    @PostConstruct
    public void init() throws SQLException {

        DataSource source;

        final Statement statement = sqlSessionFactory.openSession().getConnection().createStatement();


        final ResultSet showTables = statement.executeQuery("show tables");
        final ResultSetMetaData metaData = showTables.getMetaData();

        final ResultSet resultSet = statement.executeQuery("select * from person");
        final ResultSets resultSets = ResultSets.from(resultSet);

        System.out.println();
    }

}

