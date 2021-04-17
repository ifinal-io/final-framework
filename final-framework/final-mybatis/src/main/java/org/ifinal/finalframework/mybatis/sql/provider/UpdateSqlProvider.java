package org.ifinal.finalframework.mybatis.sql.provider;

import org.springframework.lang.NonNull;

import org.ifinal.finalframework.core.IEntity;
import org.ifinal.finalframework.data.annotation.Metadata;
import org.ifinal.finalframework.data.query.DefaultQEntityFactory;
import org.ifinal.finalframework.data.query.QueryProvider;
import org.ifinal.finalframework.data.util.Velocities;
import org.ifinal.finalframework.mybatis.sql.AbsMapperSqlProvider;
import org.ifinal.finalframework.mybatis.sql.ScriptMapperHelper;
import org.ifinal.finalframework.query.Criterion;
import org.ifinal.finalframework.query.CriterionAttributes;
import org.ifinal.finalframework.query.QEntity;
import org.ifinal.finalframework.query.QProperty;
import org.ifinal.finalframework.query.Query;
import org.ifinal.finalframework.query.Update;
import org.ifinal.finalframework.util.Asserts;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.type.TypeHandler;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class UpdateSqlProvider implements AbsMapperSqlProvider, ScriptSqlProvider {

    private static final String DEFAULT_WRITER = "#{${value}#if($javaType),javaType=$!{javaType.canonicalName}#end"
        + "#if($typeHandler),typeHandler=$!{typeHandler.canonicalName}#end}";

    private static final String PROPERTIES_PARAMETER_NAME = "properties";

    private static final String SELECTIVE_PARAMETER_NAME = "selective";

    private static final String ENTITY_PARAMETER_NAME = "entity";

    private static final String UPDATE_PARAMETER_NAME = "update";

    private static final String IDS_PARAMETER_NAME = "ids";

    private static final String QUERY_PARAMETER_NAME = "query";

    /**
     * @param context    context
     * @param parameters parameters
     * @return sql
     */
    public String update(final ProviderContext context, final Map<String, Object> parameters) {

        return provide(context, parameters);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void doProvide(final StringBuilder sql, final ProviderContext context,
        final Map<String, Object> parameters) {

        final Object query = parameters.get(QUERY_PARAMETER_NAME);

        Class<?> entity = getEntityClass(context.getMapperType());
        final QEntity<?, ?> properties = DefaultQEntityFactory.INSTANCE.create(entity);
        parameters.put(PROPERTIES_PARAMETER_NAME, properties);

        sql.append("<trim prefix=\"UPDATE\">").append("${table}").append("</trim>");

        sql.append("<set>");

        if (parameters.containsKey(UPDATE_PARAMETER_NAME) && parameters.get(UPDATE_PARAMETER_NAME) != null) {
            final Update updates = (Update) parameters.get(UPDATE_PARAMETER_NAME);
            for (int i = 0; i < updates.size(); i++) {
                Criterion criterion = updates.get(i);

                if (criterion instanceof CriterionAttributes) {
                    CriterionAttributes fragment = new CriterionAttributes();
                    fragment.putAll((CriterionAttributes) criterion);
                    fragment.setValue(String.format("update[%d]", i));
                    String value = Velocities.getValue(fragment.getExpression(), fragment);
                    sql.append(value);
                } else {
                    throw new IllegalArgumentException("update not support criterion of " + criterion.getClass());
                }

            }

//            for (UpdateSetOperation updateSetOperation : updates) {
//                updateSetOperation.apply(sql, String.format("update[%d]", index++));
//            }
        } else {
            appendEntitySet(sql, properties);
        }

        appendVersionProperty(sql, properties);

        sql.append("</set>");

        if (parameters.containsKey(IDS_PARAMETER_NAME) && parameters.get(IDS_PARAMETER_NAME) != null) {
            sql.append(whereIdsNotNull());
        } else if (query instanceof Query) {
            QueryProvider provider = query((Query) query);

            Optional.ofNullable(provider.where()).ifPresent(sql::append);
            Optional.ofNullable(provider.orders()).ifPresent(sql::append);
            Optional.ofNullable(provider.limit()).ifPresent(sql::append);

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

    /**
     * @param sql    sql
     * @param entity entity
     */
    private void appendEntitySet(final @NonNull StringBuilder sql, final @NonNull QEntity<?, ?> entity) {

        entity.stream()
            .filter(QProperty::isModifiable)
            .forEach(property -> {
                // <if test="properties.property.hasView(view)>"
                sql.append("<if test=\"properties.getRequiredProperty('")
                    .append(property.getPath())
                    .append("').hasView(view)\">");

                final Metadata metadata = new Metadata();

                metadata.setProperty(property.getName());
                metadata.setColumn(property.getColumn());
                metadata.setValue("entity." + property.getPath());
                metadata.setJavaType(property.getType());
                if (Objects.nonNull(property.getTypeHandler())) {
                    metadata.setTypeHandler((Class<? extends TypeHandler>) property.getTypeHandler());
                }

                final String writer = Asserts.isBlank(property.getWriter()) ? DEFAULT_WRITER : property.getWriter();
                final String value = Velocities.getValue(writer, metadata);

                // <choose>
                sql.append("<choose>");

                final String testWithSelective = ScriptMapperHelper
                    .formatTest(ENTITY_PARAMETER_NAME, property.getPath(), true);

                final String selectiveTest =
                    testWithSelective == null ? SELECTIVE_PARAMETER_NAME : "selective and " + testWithSelective;

                // <when test="selective and entity.path != null">
                sql.append("<when test=\"").append(selectiveTest).append("\">")
                    // property.column = entity.path
                    .append(property.getColumn()).append(" = ").append(value).append(",")
                    // </when>
                    .append("</when>");

                final String testNotWithSelective = ScriptMapperHelper
                    .formatTest(ENTITY_PARAMETER_NAME, property.getPath(), false);
                final String notSelectiveTest =
                    testNotWithSelective == null ? "!selective" : "!selective and " + testNotWithSelective;

                sql.append("<when test=\"").append(notSelectiveTest).append("\">")
                    .append(property.getColumn()).append(" = ").append(value).append(",")
                    .append("</when>");

                if (testNotWithSelective != null) {
                    sql.append("<otherwise>")
                        .append(property.getColumn()).append(" = ").append("null").append(",")
                        .append("</otherwise>");
                }

                sql.append("</choose>");

                sql.append("</if>");
            });
    }

    private void appendVersionProperty(final StringBuilder sql, final QEntity<?, ?> entity) {

        if (!entity.hasVersionProperty()) {
            return;
        }

        sql.append("<if test=\"properties.hasVersionProperty()\">");
        String version = entity.getVersionProperty().getColumn();
        sql.append(version)
            .append(" = ")
            .append(version)
            .append(" + 1,");

        sql.append("</if>");
    }

}

