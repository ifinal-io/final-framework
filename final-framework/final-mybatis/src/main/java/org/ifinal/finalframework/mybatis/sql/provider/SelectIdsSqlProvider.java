package org.ifinal.finalframework.mybatis.sql.provider;

import org.apache.ibatis.builder.annotation.ProviderContext;
import org.ifinal.finalframework.annotation.IEntity;
import org.ifinal.finalframework.annotation.IQuery;
import org.ifinal.finalframework.data.query.QEntity;
import org.ifinal.finalframework.data.query.Query;
import org.ifinal.finalframework.data.query.sql.AnnotationQueryProvider;
import org.ifinal.finalframework.mybatis.sql.AbsMapperSqlProvider;

import java.util.Map;

/**
 * @author likly
 * @version 1.0.0
 * @see org.ifinal.finalframework.mybatis.mapper.AbsMapper#selectIds(String, IQuery)
 * @since 1.0.0
 */
public class SelectIdsSqlProvider implements AbsMapperSqlProvider {

    /**
     * @param context    context
     * @param parameters parameters
     * @return sql
     * @see org.ifinal.finalframework.mybatis.mapper.AbsMapper#selectIds(String, IQuery)
     */
    public String selectIds(ProviderContext context, Map<String, Object> parameters) {
        return provide(context, parameters);
    }

    @Override
    public void doProvide(StringBuilder sql, ProviderContext context, Map<String, Object> parameters) {

        Object query = parameters.get("query");

        final Class<?> entity = getEntityClass(context.getMapperType());
        final QEntity<?, ?> properties = QEntity.from(entity);
        parameters.put("entity", properties);

        /*
         * <trim prefix="SELECT">
         *      columns
         * </trim>
         */
        sql.append("<trim prefix=\"SELECT\" suffixOverrides=\",\">");
        sql.append(properties.getIdProperty().getColumn());
        sql.append("</trim>");
        /*
         * <trim prefix="FROM">
         *     ${table}
         * </trim>
         */
        sql.append("<trim prefix=\"FROM\">")
                .append("${table}")
                .append("</trim>");


        if (query instanceof Query) {
            ((Query) query).apply(sql, "query");
        } else if (query != null) {
            sql.append(AnnotationQueryProvider.INSTANCE.provide("query", (Class<? extends IEntity<?>>) entity, query.getClass()));
        }
    }
}
