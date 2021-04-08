package org.ifinal.finalframework.mybatis.sql.provider;

import org.ifinal.finalframework.annotation.core.IEntity;
import org.ifinal.finalframework.annotation.core.IQuery;
import org.ifinal.finalframework.data.query.Query;
import org.ifinal.finalframework.data.query.QueryProvider;
import org.ifinal.finalframework.mybatis.sql.AbsMapperSqlProvider;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.apache.ibatis.builder.annotation.ProviderContext;

/**
 * @author likly
 * @version 1.0.0
 * @see org.ifinal.finalframework.mybatis.mapper.AbsMapper#selectCount(String, Collection, IQuery)
 * @since 1.0.0
 */

public class SelectCountSqlProvider implements AbsMapperSqlProvider, ScriptSqlProvider {

    private static final String QUERY = "query";

    @SuppressWarnings("unused")
    public String selectCount(final ProviderContext context, final Map<String, Object> parameters) {

        return provide(context, parameters);
    }

    @Override
    public void doProvide(final StringBuilder sql, final ProviderContext context,
        final Map<String, Object> parameters) {

        Object ids = parameters.get("ids");
        Object query = parameters.get(QUERY);

        final Class<?> entity = getEntityClass(context.getMapperType());

        sql.append("<trim prefix=\"SELECT COUNT(*) FROM\">${table}</trim>");

        if (ids != null) {
            sql.append(whereIdsNotNull());
        } else if (query instanceof Query) {
            QueryProvider provider = query((Query) query);

            Optional.ofNullable(provider.where()).ifPresent(sql::append);
            Optional.ofNullable(provider.orders()).ifPresent(sql::append);
            Optional.ofNullable(provider.limit()).ifPresent(sql::append);
        } else if (query != null) {

            final QueryProvider provider = query(QUERY, (Class<? extends IEntity<?>>) entity, query.getClass());

            if (Objects.nonNull(provider.where())) {
                sql.append(provider.where());
            }

            if (Objects.nonNull(provider.orders())) {
                sql.append(provider.orders());
            }

            if (Objects.nonNull(provider.limit())) {
                sql.append(provider.limit());
            }
        }

    }

}

