package org.finalframework.mybatis.sql.provider;

import org.apache.ibatis.builder.annotation.ProviderContext;
import org.finalframework.data.query.QEntity;
import org.finalframework.mybatis.sql.AbsMapperSqlProvider;

import java.util.Map;

/**
 * @author likly
 * @version 1.0
 * @date 2020/11/15 02:03:27
 * @see org.finalframework.mybatis.mapper.AbsMapper#truncate(String)
 * @since 1.0
 */
public class TruncateSqlProvider implements AbsMapperSqlProvider {

    private static final String TRUNCATE_SQL = "truncate table ${table}";

    public String truncate(ProviderContext context, Map<String, Object> parameters) {
        return provide(context, parameters);
    }

    @Override
    public void doProvide(StringBuilder sql, ProviderContext context, Map<String, Object> parameters) {

        final Class<?> entity = getEntityClass(context.getMapperType());
        final QEntity<?, ?> properties = QEntity.from(entity);
        parameters.put("entity", properties);

        sql.append(TRUNCATE_SQL);

    }
}