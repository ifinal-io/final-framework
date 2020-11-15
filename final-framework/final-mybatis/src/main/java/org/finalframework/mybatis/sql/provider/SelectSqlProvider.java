package org.finalframework.mybatis.sql.provider;


import org.apache.ibatis.builder.annotation.ProviderContext;
import org.finalframework.annotation.IEntity;
import org.finalframework.annotation.IQuery;
import org.finalframework.annotation.data.Metadata;
import org.finalframework.data.query.QEntity;
import org.finalframework.data.query.QProperty;
import org.finalframework.data.query.Query;
import org.finalframework.data.query.sql.AnnotationQueryProvider;
import org.finalframework.data.util.Velocities;
import org.finalframework.mybatis.sql.AbsMapperSqlProvider;
import org.finalframework.util.Asserts;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

/**
 * <pre>
 *     <code>
 *         <trim prefix="SELECT" suffixOverrides=",">
 *              <if test="entity.property.hasView(view)">
 *                  property.column,
 *              </if>
 *         </trim>
 *         <trim prefix="FROM">
 *              ${table}
 *         </trim>
 *         <where>
 *             id = #{id}
 *         </where>
 *         <where>
 *             id in
 *             <foreach collection="ids" item="id" open="(" separator="," close=")">#{id}</foreach>
 *         </where>
 *         <where>
 *
 *         </where>
 *     </code>
 * </pre>
 *
 * @author likly
 * @version 1.0
 * @date 2020-04-15 00:23:52
 * @see org.finalframework.mybatis.mapper.AbsMapper#select(String, Class, Collection, IQuery)
 * @see org.finalframework.mybatis.mapper.AbsMapper#selectOne(String, Class, Serializable, IQuery)
 * @since 1.0
 */
public class SelectSqlProvider implements AbsMapperSqlProvider {

    private static final String SELECT_METHOD_NAME = "select";
    private static final String SELECT_ONE_METHOD_NAME = "selectOne";
    private static final String DEFAULT_READER = "${column}";


    public String select(ProviderContext context, Map<String, Object> parameters) {
        return provide(context, parameters);
    }

    public String selectOne(ProviderContext context, Map<String, Object> parameters) {
        return provide(context, parameters);
    }


    @Override
    public void doProvide(StringBuilder sql, ProviderContext context, Map<String, Object> parameters) {

        Object query = parameters.get("query");
        Class<?> view = (Class<?>) parameters.get("view");

        final Class<?> entity = getEntityClass(context.getMapperType());
        final QEntity<?, ?> properties = QEntity.from(entity);
        parameters.put("entity", properties);

        /*
         * <trim prefix="SELECT">
         *      columns
         * </trim>
         */
        sql.append("<trim prefix=\"SELECT\" suffixOverrides=\",\">");
        appendColumns(sql, properties, view);
        sql.append("</trim>");
        /*
         * <trim prefix="FROM">
         *     ${table}
         * </trim>
         */
        sql.append("<trim prefix=\"FROM\">")
                .append("${table}")
                .append("</trim>");


        if (SELECT_ONE_METHOD_NAME.equals(context.getMapperMethod().getName()) && parameters.get("id") != null) {
            // WHERE id = #{id}
            // <where> id = #{id} </where>
            sql.append("<where>${properties.idProperty.column} = #{id}</where>");
        } else if (SELECT_METHOD_NAME.equals(context.getMapperMethod().getName()) && parameters.get("ids") != null) {
            // WHERE id in #{ids}
            /*
             * <where>
             *     id
             *     <foreach collection="ids" item="id" open="IN (" separator="," close=")">
             *         #{id}
             *     </foreach>
             * </where>
             */
            sql.append(whereIdsNotNull());
        } else if (query instanceof Query) {
            ((Query) query).apply(sql, "query");
        } else if (query != null) {
            sql.append(AnnotationQueryProvider.INSTANCE.provide("query", (Class<? extends IEntity<?>>) entity, query.getClass()));
        }


    }

    private void appendColumns(@NonNull StringBuilder sql, @NonNull QEntity<?, ?> entity, @Nullable Class<?> view) {
        entity.stream()
                .filter(QProperty::isReadable)
                .forEach(property -> {
                    sql.append("<if test=\"entity.getRequiredProperty('")
                            .append(property.getPath())
                            .append("').hasView(view)\">");


                    final Metadata metadata = new Metadata();

                    metadata.setProperty(property.getName());
                    metadata.setColumn(property.getColumn());
                    metadata.setValue(property.getName());
                    metadata.setJavaType(property.getType());
                    metadata.setTypeHandler(property.getTypeHandler());

                    final String reader = Asserts.isBlank(property.getReader()) ? DEFAULT_READER : property.getReader();

                    sql.append(Velocities.getValue(reader, metadata));
                    sql.append(",</if>");
                });
    }


}

