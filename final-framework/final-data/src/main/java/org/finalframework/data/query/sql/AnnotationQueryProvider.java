package org.finalframework.data.query.sql;


import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.TypeHandler;
import org.finalframework.annotation.IEntity;
import org.finalframework.annotation.query.*;
import org.finalframework.data.mapping.Entity;
import org.finalframework.data.mapping.Property;
import org.finalframework.data.query.QEntity;
import org.finalframework.data.query.criterion.CriterionHandlerRegistry;
import org.finalframework.data.query.criterion.FunctionHandlerRegistry;
import org.finalframework.data.util.Velocities;
import org.finalframework.util.Asserts;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.lang.NonNull;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author likly
 * @version 1.0
 * @date 2020-07-14 18:05:29
 * @see Criterion
 * @since 1.0
 */
@Slf4j
public class AnnotationQueryProvider implements QueryProvider {

    public static final AnnotationQueryProvider INSTANCE = new AnnotationQueryProvider();

    private static final Set<String> IGNORE_ATTRIBUTES = Stream.of(
            Metadata.ATTRIBUTE_NAME_PROPERTY,
            Metadata.ATTRIBUTE_NAME_VALUE
    ).collect(Collectors.toSet());


    private AnnotationQueryProvider() {
    }

    @Override
    @NonNull
    public String provide(String expression, @NonNull Class<? extends IEntity<?>> entity, @NonNull Class<?> query) {

        final StringBuilder builder = new StringBuilder();

        builder.append("<where>");

        /**
         * @see Offset
         */
        String offset = null;
        /**
         * @see Limit
         */
        String limit = null;

        final QEntity<?, ?> properties = QEntity.from(entity);
        appendCriteria(builder, expression, properties, query, AnnotatedElementUtils.isAnnotated(query, Or.class) ? AndOr.OR : AndOr.AND);

        final Entity<?> queryEntity = Entity.from(query);
        for (Property property : queryEntity) {
            if (property.isAnnotationPresent(Offset.class)) {
                final String xml = Arrays.stream(property.getRequiredAnnotation(Offset.class).value()).map(String::trim).collect(Collectors.joining());
                final Metadata metadata = new Metadata();
                metadata.put(Metadata.ATTRIBUTE_NAME_QUERY, expression);
                metadata.put(Metadata.ATTRIBUTE_NAME_VALUE, String.format("%s.%s", expression, property.getName()));
                metadata.put(Metadata.ATTRIBUTE_NAME_PROPERTY, String.format("%s.%s", expression, property.getName()));
                offset = Velocities.getValue(xml, metadata);
            } else if (property.isAnnotationPresent(Limit.class)) {
                final String xml = Arrays.stream(property.getRequiredAnnotation(Limit.class).value()).map(String::trim).collect(Collectors.joining());
                final Metadata metadata = new Metadata();
                metadata.put(Metadata.ATTRIBUTE_NAME_QUERY, expression);
                metadata.put(Metadata.ATTRIBUTE_NAME_VALUE, String.format("%s.%s", expression, property.getName()));
                metadata.put(Metadata.ATTRIBUTE_NAME_PROPERTY, String.format("%s.%s", expression, property.getName()));
                limit = Velocities.getValue(xml, metadata);
            } else if (property.isAnnotationPresent(Criteria.class)) {
            }
        }
        builder.append("</where>");

        appendLimit(builder, offset, limit);


        return builder.toString();
    }

    private void appendCriteria(StringBuilder sql, String expression, QEntity<?, ?> entity, Class<?> query, AndOr andOr) {
        Entity.from(query)
                .forEach(property -> {
                    if (property.isAnnotationPresent(Criterion.class)) {
                        Class<? extends Annotation> annotation = property.getRequiredAnnotation(Criterion.class).value();
                        AnnotationAttributes annotationAttributes = AnnotatedElementUtils.getMergedAnnotationAttributes(property.getField(), annotation);

                        Objects.requireNonNull(annotationAttributes, "not found annotation of @" + annotation.getSimpleName() + " at " + query.getSimpleName() + "." + property.getName());

                        final Metadata metadata = new Metadata();

                        final String path = annotationAttributes.containsKey(Metadata.ATTRIBUTE_NAME_PROPERTY)
                                && Asserts.nonBlank(annotationAttributes.getString(Metadata.ATTRIBUTE_NAME_PROPERTY))
                                ? annotationAttributes.getString(Metadata.ATTRIBUTE_NAME_PROPERTY) : property.getName();

                        metadata.put(Metadata.ATTRIBUTE_NAME_AND_OR, andOr);
                        metadata.put(Metadata.ATTRIBUTE_NAME_QUERY, expression);
                        metadata.put(Metadata.ATTRIBUTE_NAME_PROPERTY, String.format("%s.%s", expression, property.getName()));
                        metadata.put(Metadata.ATTRIBUTE_NAME_COLUMN, entity.getRequiredProperty(path).getColumn());
                        metadata.put(Metadata.ATTRIBUTE_NAME_VALUE, String.format("%s.%s", expression, property.getName()));

                        if (property.isAnnotationPresent(Function.class)) {
                            final Function function = property.getRequiredAnnotation(Function.class);
                            metadata.put(Metadata.ATTRIBUTE_NAME_COLUMN, FunctionHandlerRegistry.getInstance().get(function.handler()).handle(function, metadata));
                        }

                        //append annotation attributes
                        for (Map.Entry<String, Object> entry : annotationAttributes.entrySet()) {
                            if (IGNORE_ATTRIBUTES.contains(entry.getKey())) {
                                continue;
                            }
                            if (Metadata.ATTRIBUTE_NAME_JAVA_TYPE.equals(entry.getKey()) && !Object.class.equals(entry.getValue())) {
                                metadata.put(Metadata.ATTRIBUTE_NAME_JAVA_TYPE, entry.getValue());
                            } else if (Metadata.ATTRIBUTE_NAME_TYPE_HANDLER.equals(entry.getKey()) && !TypeHandler.class.equals(entry.getValue())) {
                                metadata.put(Metadata.ATTRIBUTE_NAME_TYPE_HANDLER, entry.getValue());
                            }
                        }

                        final String value = CriterionHandlerRegistry.getInstance().get(CriterionHandler.class).handle(annotationAttributes, metadata);
                        sql.append(value);
                    } else if (property.isAnnotationPresent(Criteria.class)) {
                        sql.append("<if test=\"").append(expression).append(".").append(property.getName()).append(" != null\">");
                        sql.append("<trim prefix=\" ").append(andOr.name()).append(" (\" suffix=\")\" prefixOverrides=\"AND |OR \">");
                        appendCriteria(sql, expression + "." + property.getName(), entity, property.getType(), property.isAnnotationPresent(Or.class) ? AndOr.OR : AndOr.AND);
                        sql.append("</trim>");
                        sql.append("</if>");
                    }
                });
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
     * @param sql
     * @param offset
     * @param limit
     */
    private void appendLimit(StringBuilder sql, String offset, String limit) {
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

