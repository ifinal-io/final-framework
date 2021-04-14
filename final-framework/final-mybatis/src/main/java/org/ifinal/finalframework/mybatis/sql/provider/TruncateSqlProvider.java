package org.ifinal.finalframework.mybatis.sql.provider;

import org.ifinal.finalframework.data.query.DefaultQEntityFactory;
import org.ifinal.finalframework.mybatis.sql.AbsMapperSqlProvider;
import org.ifinal.finalframework.query.QEntity;

import java.util.Map;

import org.apache.ibatis.builder.annotation.ProviderContext;

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
    public void doProvide(final StringBuilder sql, final ProviderContext context,
        final Map<String, Object> parameters) {

        final Class<?> entity = getEntityClass(context.getMapperType());
        final QEntity<?, ?> properties = DefaultQEntityFactory.INSTANCE.create(entity);
        parameters.put("entity", properties);

        sql.append(TRUNCATE_SQL);

    }

}
