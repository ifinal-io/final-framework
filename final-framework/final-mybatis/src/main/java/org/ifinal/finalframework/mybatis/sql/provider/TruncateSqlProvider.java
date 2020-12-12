package org.ifinal.finalframework.mybatis.sql.provider;

import org.apache.ibatis.builder.annotation.ProviderContext;
import org.ifinal.finalframework.data.query.QEntity;
import org.ifinal.finalframework.mybatis.sql.AbsMapperSqlProvider;

import java.util.Map;

/**
 * @author likly
 * @version 1.0.0
 * @see org.ifinal.finalframework.mybatis.mapper.AbsMapper#truncate(String)
 * @since 1.0.0
 */
public class TruncateSqlProvider implements AbsMapperSqlProvider {

    private static final String TRUNCATE_SQL = "truncate table ${table}";

    public String truncate(final ProviderContext context, final Map<String, Object> parameters) {

        return provide(context, parameters);
    }

    @Override
    public void doProvide(final StringBuilder sql, final ProviderContext context, final Map<String, Object> parameters) {

        final Class<?> entity = getEntityClass(context.getMapperType());
        final QEntity<?, ?> properties = QEntity.from(entity);
        parameters.put("entity", properties);

        sql.append(TRUNCATE_SQL);

    }
}
