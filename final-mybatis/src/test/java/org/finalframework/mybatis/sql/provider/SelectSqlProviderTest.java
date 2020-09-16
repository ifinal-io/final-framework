package org.finalframework.mybatis.sql.provider;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.builder.annotation.ProviderSqlSource;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.session.Configuration;
import org.finalframework.data.query.Query;
import org.finalframework.mybatis.mapper.AbsMapper;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * @author likly
 * @version 1.0
 * @date 2020-07-17 15:32:44
 * @since 1.0
 */
@Slf4j
class SelectSqlProviderTest {

    @Test
    void selectOne() throws NoSuchMethodException {

        final Method selectOne = AbsMapper.class.getMethod("selectOne", String.class, Class.class, Serializable.class, Query.class);
        /**
         * @see ProviderSqlSource
         */
        final ProviderSqlSource providerSqlSource = new ProviderSqlSource(new Configuration(), selectOne.getAnnotation(SelectProvider.class), PersonMapper.class, selectOne);
        final HashMap<String, Object> parameters = new HashMap<>();

        parameters.put("table", "person");
        parameters.put("view", null);
        parameters.put("id", null);
        final PersonQuery query = new PersonQuery();
        query.setLocation(new Point());
        query.setDistance(1L);
        parameters.put("query", query);

        final BoundSql boundSql = providerSqlSource.getBoundSql(parameters);

        final String sql = boundSql.getSql();
        logger.info(sql);
    }
}