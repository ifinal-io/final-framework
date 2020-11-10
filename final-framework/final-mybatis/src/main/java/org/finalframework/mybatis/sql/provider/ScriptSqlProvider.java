package org.finalframework.mybatis.sql.provider;


import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.builder.annotation.ProviderSqlSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @author likly
 * @version 1.0
 * @date 2020-06-03 21:42:15
 * @see ProviderSqlSource
 * @since 1.0
 */
public interface ScriptSqlProvider extends SqlProvider {

    @Override
    default String provide(ProviderContext context, Map<String, Object> parameters) {
        final Logger logger = LoggerFactory.getLogger(context.getMapperType() + "." + context.getMapperMethod().getName());
        final StringBuilder sql = new StringBuilder();
        sql.append("<script>");
        doProvide(sql, context, parameters);
        sql.append("</script>");
        String value = sql.toString();
        logger.info("sql ==> {}", value);
        return value;
    }

    void doProvide(StringBuilder sql, ProviderContext context, Map<String, Object> parameters);


}

