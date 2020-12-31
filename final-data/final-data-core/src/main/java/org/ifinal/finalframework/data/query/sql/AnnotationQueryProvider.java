package org.ifinal.finalframework.data.query.sql;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.LinkedHashMap;
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
import org.ifinal.finalframework.annotation.query.Limit;
import org.ifinal.finalframework.annotation.query.Offset;
import org.ifinal.finalframework.annotation.query.Or;
import org.ifinal.finalframework.annotation.query.Order;
import org.ifinal.finalframework.annotation.query.function.Function;
import org.ifinal.finalframework.data.mapping.Entity;
import org.ifinal.finalframework.data.mapping.Property;
import org.ifinal.finalframework.data.query.QEntity;
import org.ifinal.finalframework.data.query.QueryProvider;
import org.ifinal.finalframework.data.query.criterion.CriterionHandlerRegistry;
import org.ifinal.finalframework.data.util.Velocities;
import org.ifinal.finalframework.util.Asserts;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationAttributes;

/**
 * @author likly
 * @version 1.0.0
 * @see org.ifinal.finalframework.data.query.criterion.Criterion
 * @since 1.0.0
 */
@Slf4j
public final class AnnotationQueryProvider implements QueryProvider {

    private final String where;

    private final String orders;

    private final String limit;

    public static final String FORMAT = "%s.%s";

    private static final Set<String> IGNORE_ATTRIBUTES = Stream.of(
        CriterionAttributes.ATTRIBUTE_NAME_PROPERTY,
        CriterionAttributes.ATTRIBUTE_NAME_VALUE
    ).collect(Collectors.toSet());

    public AnnotationQueryProvider(final String expression, final Class<? extends IEntity> entity, final Class query) {

        final StringBuilder where = new StringBuilder();
        where.append("<where>");
        String offset = null;
        String limit = null;

        final QEntity<?, ?> properties = QEntity.from(entity);
        appendCriteria(where, expression, properties, query,
            AnnotatedElementUtils.isAnnotated(query, Or.class) ? AndOr.OR : AndOr.AND);

        final Map<Integer, String> orders = new LinkedHashMap<>();
        CriterionSqlProvider criterionSqlProvider = CriterionHandlerRegistry.getInstance().get(CriterionSqlProvider.class);
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
            } else if (property.isAnnotationPresent(Order.class)) {
                Order order = property.getRequiredAnnotation(Order.class);

                AnnotationAttributes attributes = AnnotatedElementUtils
                    .getMergedAnnotationAttributes(property.getField(), Order.class);
                final CriterionAttributes metadata = new CriterionAttributes();

                final String path = attributes.containsKey(CriterionAttributes.ATTRIBUTE_NAME_PROPERTY)
                    && Asserts.nonBlank(attributes.getString(CriterionAttributes.ATTRIBUTE_NAME_PROPERTY))
                    ? attributes.getString(CriterionAttributes.ATTRIBUTE_NAME_PROPERTY) : property.getName();

                metadata.put(CriterionAttributes.ATTRIBUTE_NAME_QUERY, expression);
                metadata.put(CriterionAttributes.ATTRIBUTE_NAME_COLUMN, properties.getRequiredProperty(path).getColumn());
                metadata.put(CriterionAttributes.ATTRIBUTE_NAME_VALUE, String.format(FORMAT, expression, property.getName()));
                orders.put(order.order(), criterionSqlProvider.order(attributes, metadata));

            }
        }
        where.append("</where>");

        this.where = where.toString();
        this.orders = buildOrders(orders);
        this.limit = buildLimit(offset, limit);

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

                    AnnotationAttributes criterionAttributes = AnnotatedElementUtils
                        .getMergedAnnotationAttributes(field, annotation);

                    Objects.requireNonNull(criterionAttributes,
                        "not found annotation of @" + annotation.getSimpleName() + " at " + query.getSimpleName() + "."
                            + property.getName());

                    final CriterionAttributes metadata = new CriterionAttributes();

                    final String path = criterionAttributes.containsKey(CriterionAttributes.ATTRIBUTE_NAME_PROPERTY)
                        && Asserts.nonBlank(criterionAttributes.getString(CriterionAttributes.ATTRIBUTE_NAME_PROPERTY))
                        ? criterionAttributes.getString(CriterionAttributes.ATTRIBUTE_NAME_PROPERTY) : property.getName();

                    metadata.put(CriterionAttributes.ATTRIBUTE_NAME_AND_OR, andOr);
                    metadata.put(CriterionAttributes.ATTRIBUTE_NAME_QUERY, expression);
                    metadata
                        .put(CriterionAttributes.ATTRIBUTE_NAME_PROPERTY, String.format(FORMAT, expression, property.getName()));
                    metadata.put(CriterionAttributes.ATTRIBUTE_NAME_COLUMN, entity.getRequiredProperty(path).getColumn());
                    metadata.put(CriterionAttributes.ATTRIBUTE_NAME_VALUE, String.format(FORMAT, expression, property.getName()));

                    CriterionSqlProvider criterionSqlProvider = CriterionHandlerRegistry.getInstance().get(CriterionSqlProvider.class);

                    // process @Function annotation
                    if (property.isAnnotationPresent(Function.class)) {
                        AnnotationAttributes functionAttributes = findFunctionAnnotationAttributes(property);
                        Objects.requireNonNull(functionAttributes);
                        appendAnnotationAttributesToMetadata(functionAttributes, metadata);
                        criterionSqlProvider.function(functionAttributes, metadata);
                    }

                    appendAnnotationAttributesToMetadata(criterionAttributes, metadata);

                    // process @Criterion annotation
                    final String value = criterionSqlProvider.provide(criterionAttributes, metadata);
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

    private AnnotationAttributes findFunctionAnnotationAttributes(final Property property) {
        final Function function = property.getRequiredAnnotation(Function.class);
        return AnnotatedElementUtils.getMergedAnnotationAttributes(property.getField(), function.value());

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

    private String buildOrders(final Map<Integer, String> orders) {
        final StringBuilder sql = new StringBuilder();
        if (orders.isEmpty()) {
            return null;
        }
        sql.append("<trim prefix=\" ORDER BY \" suffixOverrides=\",\">");
        orders.keySet().stream()
            .sorted()
            .map(orders::get)
            .forEach(sql::append);
        sql.append("</trim>");

        return sql.toString();
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
     * @param offset offset
     * @param limit  set
     */
    private String buildLimit(final String offset, final String limit) {

        final StringBuilder sql = new StringBuilder();

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

        return sql.toString();
    }

    @Override
    public String where() {
        return this.where;
    }

    @Override
    public String orders() {
        return this.orders;
    }

    @Override
    public String limit() {
        return this.limit;
    }

}

