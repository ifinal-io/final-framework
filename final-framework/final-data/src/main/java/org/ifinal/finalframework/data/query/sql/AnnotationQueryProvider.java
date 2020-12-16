package org.ifinal.finalframework.data.query.sql;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.TypeHandler;
import org.ifinal.finalframework.annotation.core.IEntity;
import org.ifinal.finalframework.annotation.query.AndOr;
import org.ifinal.finalframework.annotation.query.Criteria;
import org.ifinal.finalframework.annotation.query.Criterion;
import org.ifinal.finalframework.annotation.query.CriterionAttributes;
import org.ifinal.finalframework.annotation.query.CriterionSqlProvider;
import org.ifinal.finalframework.annotation.query.Function;
import org.ifinal.finalframework.annotation.query.Limit;
import org.ifinal.finalframework.annotation.query.Offset;
import org.ifinal.finalframework.annotation.query.Or;
import org.ifinal.finalframework.annotation.query.QueryProvider;
import org.ifinal.finalframework.data.mapping.Entity;
import org.ifinal.finalframework.data.mapping.Property;
import org.ifinal.finalframework.data.query.QEntity;
import org.ifinal.finalframework.data.query.criterion.CriterionHandlerRegistry;
import org.ifinal.finalframework.data.query.criterion.FunctionHandlerRegistry;
import org.ifinal.finalframework.data.util.Velocities;
import org.ifinal.finalframework.util.Asserts;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.lang.NonNull;

/**
 * @author likly
 * @version 1.0.0
 * @see Criterion
 * @since 1.0.0
 */
@Slf4j
public final class AnnotationQueryProvider implements QueryProvider {

    public static final AnnotationQueryProvider INSTANCE = new AnnotationQueryProvider();

    public static final String FORMAT = "%s.%s";

    private static final Set<String> IGNORE_ATTRIBUTES = Stream.of(
        CriterionAttributes.ATTRIBUTE_NAME_PROPERTY,
        CriterionAttributes.ATTRIBUTE_NAME_VALUE
    ).collect(Collectors.toSet());

    private AnnotationQueryProvider() {
    }

    @Override
    @NonNull
    public String provide(final String expression, final @NonNull Class<? extends IEntity<?>> entity,
        final @NonNull Class<?> query) {

        final StringBuilder builder = new StringBuilder();

        builder.append("<where>");

        String offset = null;
        String limit = null;

        final QEntity<?, ?> properties = QEntity.from(entity);
        appendCriteria(builder, expression, properties, query,
            AnnotatedElementUtils.isAnnotated(query, Or.class) ? AndOr.OR : AndOr.AND);

        final Entity<?> queryEntity = Entity.from(query);
        for (Property property : queryEntity) {
            if (property.isAnnotationPresent(Offset.class)) {
                final String xml = Arrays.stream(property.getRequiredAnnotation(Offset.class).value()).map(String::trim)
                    .collect(Collectors.joining());
                offset = Velocities.getValue(xml, buildLimitOffsetMetadata(expression, property));
            } else if (property.isAnnotationPresent(Limit.class)) {
                final String xml = Arrays.stream(property.getRequiredAnnotation(Limit.class).value()).map(String::trim)
                    .collect(Collectors.joining());
                limit = Velocities.getValue(xml, buildLimitOffsetMetadata(expression, property));
            }
        }
        builder.append("</where>");

        appendLimit(builder, offset, limit);

        return builder.toString();
    }

    private CriterionAttributes buildLimitOffsetMetadata(final String expression, final Property property) {

        final CriterionAttributes metadata = new CriterionAttributes();
        metadata.put(CriterionAttributes.ATTRIBUTE_NAME_QUERY, expression);
        metadata.put(CriterionAttributes.ATTRIBUTE_NAME_VALUE, String.format(FORMAT, expression, property.getName()));
        metadata.put(CriterionAttributes.ATTRIBUTE_NAME_PROPERTY, String.format(FORMAT, expression, property.getName()));
        return metadata;
    }

    private void appendCriteria(final StringBuilder sql, final String expression, final QEntity<?, ?> entity,
        final Class<?> query, final AndOr andOr) {

        Entity.from(query)
            .forEach(property -> {
                if (property.isAnnotationPresent(Criterion.class)) {
                    Class<? extends Annotation> annotation = property.getRequiredAnnotation(Criterion.class).value();
                    Field field = property.getField();
                    Objects.requireNonNull(field, "property filed can not be null:" + property.getName());
                    AnnotationAttributes annotationAttributes = AnnotatedElementUtils
                        .getMergedAnnotationAttributes(field, annotation);
                    Objects.requireNonNull(annotationAttributes,
                        "not found annotation of @" + annotation.getSimpleName() + " at " + query.getSimpleName() + "."
                            + property.getName());

                    final CriterionAttributes metadata = new CriterionAttributes();

                    final String path = annotationAttributes.containsKey(CriterionAttributes.ATTRIBUTE_NAME_PROPERTY)
                        && Asserts.nonBlank(annotationAttributes.getString(CriterionAttributes.ATTRIBUTE_NAME_PROPERTY))
                        ? annotationAttributes.getString(CriterionAttributes.ATTRIBUTE_NAME_PROPERTY) : property.getName();

                    metadata.put(CriterionAttributes.ATTRIBUTE_NAME_AND_OR, andOr);
                    metadata.put(CriterionAttributes.ATTRIBUTE_NAME_QUERY, expression);
                    metadata
                        .put(CriterionAttributes.ATTRIBUTE_NAME_PROPERTY, String.format(FORMAT, expression, property.getName()));
                    metadata.put(CriterionAttributes.ATTRIBUTE_NAME_COLUMN, entity.getRequiredProperty(path).getColumn());
                    metadata.put(CriterionAttributes.ATTRIBUTE_NAME_VALUE, String.format(FORMAT, expression, property.getName()));

                    parseFunctionAnnotation(property, metadata);
                    appendAnnotationAttributesToMetadata(annotationAttributes, metadata);

                    final String value = CriterionHandlerRegistry.getInstance().get(CriterionSqlProvider.class)
                        .provide(annotationAttributes.getStringArray("value"), metadata);
                    sql.append(value);
                } else if (property.isAnnotationPresent(Criteria.class)) {
                    sql.append("<if test=\"").append(expression).append(".").append(property.getName())
                        .append(" != null\">");
                    sql.append("<trim prefix=\" ").append(andOr.name())
                        .append(" (\" suffix=\")\" prefixOverrides=\"AND |OR \">");
                    appendCriteria(sql, expression + "." + property.getName(), entity, property.getType(),
                        property.isAnnotationPresent(Or.class) ? AndOr.OR : AndOr.AND);
                    sql.append("</trim>");
                    sql.append("</if>");
                }
            });
    }

    private void parseFunctionAnnotation(final Property property, final CriterionAttributes metadata) {

        if (!property.isAnnotationPresent(Function.class)) {
            return;
        }

        final Function function = property.getRequiredAnnotation(Function.class);
        metadata.put(CriterionAttributes.ATTRIBUTE_NAME_COLUMN,
            FunctionHandlerRegistry.getInstance().get(function.handler()).handle(function, metadata));

    }

    private void appendAnnotationAttributesToMetadata(final AnnotationAttributes annotationAttributes,
        final CriterionAttributes metadata) {
        //append annotation attributes
        for (Map.Entry<String, Object> entry : annotationAttributes.entrySet()) {
            if (IGNORE_ATTRIBUTES.contains(entry.getKey())
                || (CriterionAttributes.ATTRIBUTE_NAME_JAVA_TYPE.equals(entry.getKey()) && Object.class.equals(entry.getValue()))
                || (CriterionAttributes.ATTRIBUTE_NAME_TYPE_HANDLER.equals(entry.getKey()) && TypeHandler.class
                .equals(entry.getValue()))) {
                continue;
            }
            metadata.put(entry.getKey(), entry.getValue());
        }
    }

    /**
     * <pre>
     *     <code>
     *         <trim prefix="LIMIT">
     *              <if test="offset != null">
     *                  #{offset},
     *              </if>
     *              <if test="limit != null">
     *                  #{limit}
     *              </if>
     *         </trim>
     *     </code>
     * </pre>
     *
     * @param sql    sql
     * @param offset offset
     * @param limit  set
     */
    private void appendLimit(final StringBuilder sql, final String offset, final String limit) {

        if (offset != null || limit != null) {

            sql.append("<trim prefix=\"LIMIT\">");
            if (offset != null) {
                sql.append(offset);
            }
            if (limit != null) {
                sql.append(limit);
            }
            sql.append("</trim>");
        }

    }

}

