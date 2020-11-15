package org.finalframework.mybatis.sql.provider;

import org.apache.ibatis.builder.annotation.ProviderContext;
import org.finalframework.annotation.IEntity;
import org.finalframework.annotation.IQuery;
import org.finalframework.data.query.QEntity;
import org.finalframework.data.query.Query;
import org.finalframework.data.query.sql.AnnotationQueryProvider;
import org.finalframework.mybatis.sql.AbsMapperSqlProvider;

import java.util.Map;

/**
 * <pre>
 *     <trim prefix="SELECT">
 *
 *     </trim>
 *     <trim prefix="FROM">
 *          ${table}
 *     </trim>
 *     <where>
 *
 *     </where>
 * </pre>
 *
 * @author likly
 * @version 1.0
 * @date 2020/11/15 01:42:17
 * @see org.finalframework.mybatis.mapper.AbsMapper#selectIds(String, IQuery)
 * @since 1.0
 */
public class SelectIdsSqlProvider implements AbsMapperSqlProvider {

    /**
     * @param context
     * @param parameters
     * @return
     * @see org.finalframework.mybatis.mapper.AbsMapper#selectIds(String, IQuery)
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
