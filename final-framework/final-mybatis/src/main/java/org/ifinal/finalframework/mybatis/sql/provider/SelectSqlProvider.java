package org.ifinal.finalframework.mybatis.sql.provider;

import org.springframework.lang.NonNull;

import org.ifinal.finalframework.annotation.core.IEntity;
import org.ifinal.finalframework.annotation.core.IQuery;
import org.ifinal.finalframework.annotation.data.Metadata;
import org.ifinal.finalframework.data.query.QEntity;
import org.ifinal.finalframework.data.query.QProperty;
import org.ifinal.finalframework.data.query.Query;
import org.ifinal.finalframework.data.query.QueryProvider;
import org.ifinal.finalframework.data.util.Velocities;
import org.ifinal.finalframework.mybatis.sql.AbsMapperSqlProvider;
import org.ifinal.finalframework.util.Asserts;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

import org.apache.ibatis.builder.annotation.ProviderContext;

/**
 * @author likly
 * @version 1.0.0
 * @see org.ifinal.finalframework.mybatis.mapper.AbsMapper#select(String, Class, Collection, IQuery)
 * @see org.ifinal.finalframework.mybatis.mapper.AbsMapper#selectOne(String, Class, Serializable, IQuery)
 * @since 1.0.0
 */
public class SelectSqlProvider implements AbsMapperSqlProvider {

    public static final String QUERY_PARAMETER_NAME = "query";

    private static final String SELECT_METHOD_NAME = "select";

    private static final String SELECT_ONE_METHOD_NAME = "selectOne";

    private static final String DEFAULT_READER = "${column}";

    public String select(final ProviderContext context, final Map<String, Object> parameters) {

        return provide(context, parameters);
    }

    public String selectOne(final ProviderContext context, final Map<String, Object> parameters) {

        return provide(context, parameters);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void doProvide(final StringBuilder sql, final ProviderContext context,
        final Map<String, Object> parameters) {

        final Class<?> entity = getEntityClass(context.getMapperType());
        final QEntity<?, ?> properties = QEntity.from(entity);
        parameters.put("entity", properties);

        sql.append("<trim prefix=\"SELECT\" suffixOverrides=\",\">");
        appendColumns(sql, properties);
        sql.append("</trim>");
        sql.append("<trim prefix=\"FROM\">")
            .append("${table}")
            .append("</trim>");

        Object query = parameters.get(QUERY_PARAMETER_NAME);

        if (SELECT_ONE_METHOD_NAME.equals(context.getMapperMethod().getName()) && parameters.get("id") != null) {
            // <where> id = #{id} </where>
            sql.append("<where>${properties.idProperty.column} = #{id}</where>");
        } else if (SELECT_METHOD_NAME.equals(context.getMapperMethod().getName()) && parameters.get("ids") != null) {
            sql.append(whereIdsNotNull());
        } else if (query instanceof Query) {
            ((Query) query).apply(sql, QUERY_PARAMETER_NAME);
        } else if (query != null) {

            final QueryProvider provider = query(QUERY_PARAMETER_NAME, (Class<? extends IEntity<?>>) entity, query.getClass());

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

    private void appendColumns(final @NonNull StringBuilder sql, final @NonNull QEntity<?, ?> entity) {

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

