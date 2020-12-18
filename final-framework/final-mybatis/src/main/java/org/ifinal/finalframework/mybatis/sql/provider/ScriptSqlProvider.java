package org.ifinal.finalframework.mybatis.sql.provider;

import java.util.Map;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.builder.annotation.ProviderSqlSource;
import org.ifinal.finalframework.util.XmlFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author likly
 * @version 1.0.0
 * @see ProviderSqlSource
 * @since 1.0.0
 */
public interface ScriptSqlProvider extends SqlProvider {

    @Override
    default String provide(ProviderContext context, Map<String, Object> parameters) {

        final Logger logger = LoggerFactory
            .getLogger(context.getMapperType().getName() + "." + context.getMapperMethod().getName());
        StringBuilder sql = new StringBuilder();
        sql.append("<script>");
        doProvide(sql, context, parameters);
        sql.append("</script>");
        String value = sql.toString();
        if (logger.isDebugEnabled()) {
            String script = XmlFormatter.format(value);

            String[] nodes = script.split("\n");

            for (final String node : nodes) {
                logger.debug("{}", node);

            }

        }
        return value;
    }

    void doProvide(StringBuilder sql, ProviderContext context, Map<String, Object> parameters);

}

