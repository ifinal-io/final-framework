

package org.finalframework.data.query.sql;


import lombok.extern.slf4j.Slf4j;
import org.finalframework.annotation.IEntity;
import org.finalframework.annotation.query.*;
import org.finalframework.core.Asserts;
import org.finalframework.data.mapping.Entity;
import org.finalframework.data.mapping.Property;
import org.finalframework.data.query.QEntity;
import org.finalframework.data.query.criterion.CriterionHandlerRegistry;
import org.finalframework.data.query.criterion.FunctionHandlerRegistry;
import org.finalframework.data.util.Velocities;
import org.springframework.core.annotation.AnnotationUtils;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author likly
 * @version 1.0
 * @date 2020-07-14 18:05:29
 * @since 1.0
 */
@Slf4j
public class AnnotationQueryProvider implements QueryProvider {

    public static final AnnotationQueryProvider INSTANCE = new AnnotationQueryProvider();

    private AnnotationQueryProvider() {
    }

    @Override
    public String provide(String expression, Class<? extends IEntity<?>> entity, Class<?> query) {

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
        appendCriteria(builder, expression, properties, query);

        final Entity<?> queryEntity = Entity.from(query);
        for (Property property : queryEntity) {
            if (property.isAnnotationPresent(Limit.class)) {
                final String xml = Arrays.stream(property.getRequiredAnnotation(Limit.class).value()).map(String::trim).collect(Collectors.joining());

                final Metadata metadata = Metadata.builder()
                        .value(String.format("%s.%s", expression, property.getName()))
                        .path(String.format("%s.%s", expression, property.getName()))
                        .build();
                limit = Velocities.getValue(xml, metadata);
            } else if (property.isAnnotationPresent(Criteria.class)) {

            }
        }
        builder.append("</where>");

        appendLimit(builder, offset, limit);


        return builder.toString();
    }

    private void appendCriteria(StringBuilder sql, String expression, QEntity<?, ?> entity, Class<?> query) {
        final Criteria criteria = AnnotationUtils.findAnnotation(query, Criteria.class);
        AndOr andOr = criteria != null ? criteria.value() : AndOr.AND;
        Entity.from(query)
                .forEach(property -> {
                    if (property.isAnnotationPresent(Criterion.class)) {
                        final Criterion criterion = property.getRequiredAnnotation(Criterion.class);
                        final String path = Asserts.isBlank(criterion.property()) ? property.getName() : criterion.property();
                        // format the column with function
                        final Metadata metadata = Metadata.builder().andOr(andOr)
                                .query(expression)
                                .column(entity.getRequiredProperty(path).getColumn())
                                .value(String.format("%s.%s", expression, property.getName()))
                                .path(String.format("%s.%s", expression, property.getName()))
                                .attributes(Arrays.stream(criterion.attributes())
                                        .collect(Collectors.toMap(Attribute::name, Attribute::value)))
                                .build();

                        if (property.isAnnotationPresent(Function.class)) {
                            final Function function = property.getRequiredAnnotation(Function.class);
                            metadata.setColumn(FunctionHandlerRegistry.getInstance().get(function.handler()).handle(function, metadata));
                        }

                        final String value = CriterionHandlerRegistry.getInstance().get(criterion.handler()).handle(criterion, metadata);
                        sql.append(value);
                    } else if (property.isAnnotationPresent(Criteria.class)) {
                        appendCriteria(sql, expression + "." + property.getName(), entity, property.getType());
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

