package org.finalframework.spiriter.jdbc.api.controller;

import org.apache.ibatis.session.SqlSessionFactory;
import org.finalframework.data.query.Query;
import org.finalframework.spiriter.jdbc.dao.mapper.TablesMapper;
import org.finalframework.spiriter.jdbc.dao.query.QTables;
import org.finalframework.spiriter.jdbc.entity.Tables;
import org.finalframework.spiriter.jdbc.query.TablesQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;


/**
 * @author likly
 * @version 1.0
 * @date 2020-05-20 23:05:25
 * @since 1.0
 */
@RestController
@RequestMapping("/api/jdbc/tables")
public class TablesApiController implements InitializingBean {
    public static final Logger logger = LoggerFactory.getLogger(TablesApiController.class);

    @Resource
    private SqlSessionFactory sqlSessionFactory;

    private TablesMapper tablesMapper;

    @GetMapping
    public List<Tables> tables(TablesQuery query) {
        return tablesMapper.select(new Query().where(QTables.schema.eq(query.getSchema())));
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        this.tablesMapper = sqlSessionFactory.getConfiguration().getMapper(TablesMapper.class, sqlSessionFactory.openSession());
    }
}

