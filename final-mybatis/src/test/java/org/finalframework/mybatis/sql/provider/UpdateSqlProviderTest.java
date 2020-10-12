package org.finalframework.mybatis.sql.provider;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.builder.annotation.ProviderSqlSource;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.session.Configuration;
import org.finalframework.annotation.IEntity;
import org.finalframework.data.query.Query;
import org.finalframework.data.query.Update;
import org.finalframework.mybatis.mapper.AbsMapper;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author likly
 * @version 1.0
 * @date 2020-07-17 14:05:56
 * @since 1.0
 */
@Slf4j
class UpdateSqlProviderTest {

    /**
     * @throws NoSuchMethodException
     * @see UpdateSqlProvider#update(ProviderContext, Map)
     * @see AbsMapper#update(String, Class, IEntity, Update, boolean, Collection, Query)
     */
    @Test
    void update() throws NoSuchMethodException {

        final Method update = AbsMapper.class.getMethod("update", new Class[]{String.class, Class.class, IEntity.class, Update.class, boolean.class, Collection.class});
        /**
         * @see ProviderSqlSource
         */
        final ProviderSqlSource providerSqlSource = new ProviderSqlSource(new Configuration(), update.getAnnotation(UpdateProvider.class), PersonMapper.class, update);
        final HashMap<String, Object> parameters = new HashMap<>();

        parameters.put("table", "person");
//        parameters.put("view", IView.class);
        parameters.put("view", null);
        parameters.put("ignore", false);
        parameters.put("list", Arrays.asList(new Person()));

        final BoundSql boundSql = providerSqlSource.getBoundSql(parameters);

        final String sql = boundSql.getSql();
        logger.info(sql);

    }

}