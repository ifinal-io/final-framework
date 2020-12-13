package org.ifinal.finalframework.mybatis.sql.provider;

import java.util.Collection;
import java.util.Map;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.ifinal.finalframework.annotation.core.IEntity;
import org.ifinal.finalframework.annotation.core.IQuery;
import org.ifinal.finalframework.data.query.Query;
import org.ifinal.finalframework.data.query.sql.AnnotationQueryProvider;
import org.ifinal.finalframework.mybatis.sql.AbsMapperSqlProvider;

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
            ((Query) query).apply(sql, QUERY);
        } else if (query != null) {
            sql.append(AnnotationQueryProvider.INSTANCE
                .provide(QUERY, (Class<? extends IEntity<?>>) entity, query.getClass()));
        }

    }

}

