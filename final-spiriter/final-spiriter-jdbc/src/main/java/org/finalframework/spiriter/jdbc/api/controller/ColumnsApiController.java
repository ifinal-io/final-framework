

package org.finalframework.spiriter.jdbc.api.controller;

import org.apache.ibatis.session.SqlSessionFactory;
import org.finalframework.data.query.Query;
import org.finalframework.spiriter.jdbc.dao.mapper.ColumnsMapper;
import org.finalframework.spiriter.jdbc.dao.query.QColumns;
import org.finalframework.spiriter.jdbc.entity.Columns;
import org.finalframework.spiriter.jdbc.query.ColumnsQuery;
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
 * @date 2020-05-20 20:44:42
 * @since 1.0
 */
@RestController
@RequestMapping("/api/jdbc")
public class ColumnsApiController implements InitializingBean {
    public static final Logger logger = LoggerFactory.getLogger(ColumnsApiController.class);

    @Resource
    private SqlSessionFactory sqlSessionFactory;

    private ColumnsMapper columnsMapper;

    @GetMapping("/columns")
    public List<Columns> columns(ColumnsQuery query) {
        return columnsMapper.select(new Query().where(
                QColumns.schema.eq(query.getSchema()),
                QColumns.table.eq(query.getTable())
        ));
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        this.columnsMapper = sqlSessionFactory.getConfiguration().getMapper(ColumnsMapper.class, sqlSessionFactory.openSession());

    }
}

