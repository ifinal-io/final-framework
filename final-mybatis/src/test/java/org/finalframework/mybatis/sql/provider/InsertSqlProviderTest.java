package org.finalframework.mybatis.sql.provider;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.builder.annotation.ProviderSqlSource;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.session.Configuration;
import org.finalframework.annotation.IView;
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
class InsertSqlProviderTest {

    /**
     * @throws NoSuchMethodException
     * @see InsertSqlProvider#insert(ProviderContext, Map)
     * @see AbsMapper#insert(String, Class, boolean, Collection)
     */
    @Test
    void insert() throws NoSuchMethodException {

        final Method insert = AbsMapper.class.getMethod("insert", new Class[]{String.class, Class.class, boolean.class, Collection.class});
        /**
         * @see ProviderSqlSource
         */
        final ProviderSqlSource providerSqlSource = new ProviderSqlSource(new Configuration(), insert.getAnnotation(InsertProvider.class), PersonMapper.class, insert);
        final HashMap<String, Object> parameters = new HashMap<>();

        parameters.put("table", "person");
        parameters.put("view", IView.class);
        parameters.put("ignore", false);
        parameters.put("list", Arrays.asList(new Person()));

        final BoundSql boundSql = providerSqlSource.getBoundSql(parameters);

        final String sql = boundSql.getSql();
        logger.info(sql);

    }

    /**
     * @throws NoSuchMethodException
     * @see AbsMapper#replace(String, Class, Collection)
     */
    @Test
    void replace() throws NoSuchMethodException {

        final Method replace = AbsMapper.class.getMethod("replace", new Class[]{String.class, Class.class, Collection.class});
        /**
         * @see ProviderSqlSource
         */
        final ProviderSqlSource providerSqlSource = new ProviderSqlSource(new Configuration(), replace.getAnnotation(InsertProvider.class), PersonMapper.class, replace);
        final HashMap<String, Object> parameters = new HashMap<>();

        parameters.put("table", "person");
        parameters.put("view", null);
        parameters.put("view", null);
        parameters.put("ignore", false);
        parameters.put("list", Arrays.asList(new Person()));

        final BoundSql boundSql = providerSqlSource.getBoundSql(parameters);

        final String sql = boundSql.getSql();
        logger.info(sql);
    }

    /**
     * @throws NoSuchMethodException
     * @see AbsMapper#save(String, Class, Collection)
     */
    @Test
    void save() throws NoSuchMethodException {
        final Method save = AbsMapper.class.getMethod("save", new Class[]{String.class, Class.class, Collection.class});
        /**
         * @see ProviderSqlSource
         */
        final ProviderSqlSource providerSqlSource = new ProviderSqlSource(new Configuration(), save.getAnnotation(InsertProvider.class), PersonMapper.class, save);
        final HashMap<String, Object> parameters = new HashMap<>();

        parameters.put("table", "person");
        parameters.put("view", null);
        parameters.put("view", null);
        parameters.put("ignore", false);
        parameters.put("list", Arrays.asList(new Person()));

        final BoundSql boundSql = providerSqlSource.getBoundSql(parameters);

        final String sql = boundSql.getSql();
        logger.info(sql);
    }
}