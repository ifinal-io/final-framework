package com.ilikly.finalframework.mybatis.provider;

import com.ilikly.finalframework.data.mapping.Entity;
import com.ilikly.finalframework.data.provider.Provider;
import com.ilikly.finalframework.data.query.*;
import com.ilikly.finalframework.mybatis.handler.TypeHandlerRegistry;
import lombok.NonNull;
import org.apache.ibatis.type.TypeHandler;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-19 10:23
 * @since 1.0
 */
public abstract class AbsProvider<T> implements Provider<String> {

    private final TypeHandlerRegistry typeHandlerRegistry;
    private final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:dd");

    public AbsProvider(TypeHandlerRegistry typeHandlerRegistry) {
        this.typeHandlerRegistry = typeHandlerRegistry;
    }

    String getTable(@NonNull Entity<T> entity) {
        return entity.getTable();
    }

    String getJavaTypeAndTypeHandler(QProperty property) {

        final Class javaType = property.isCollectionLike() ? property.getComponentType() : property.getType();
        final Class collectionType = property.isCollectionLike() ? property.getType() : null;
        TypeHandler typeHandler = typeHandlerRegistry.getTypeHandler(javaType, collectionType, property.getPersistentType());

        if (javaType != null && typeHandler != null) {
            return String.format(",javaType = %s,typeHandler = %s", javaType.getName(), typeHandler.getClass().getName());
        }
        return "";
    }

    String getQuery(Query query) {
        if (query == null) return "";

        final StringBuilder sb = new StringBuilder();
        if (query.stream().count() > 0) {
            sb.append(" WHERE ").append(joinCriteria(query.stream()));
        }

        if (query.getSort() != null) {
            sb.append(" ORDER BY ").append(getSort(query.getSort()));
        }

        if (query.getLimit() != null) {
            sb.append(" LIMIT ").append(query.getLimit());
        }

        return sb.toString();
    }

    private String getSort(Sort sort) {
        return sort.stream()
                .map(it -> String.format("%s %s", getFormatProperty(it.getProperty()), it.getDirection().name()))
                .collect(Collectors.joining(","));
    }


    private String getFormatProperty(QProperty property) {
        return String.format("%s.%s", property.getTable(), property.getColumn());
    }

    private String buildInString(Collection<?> collection) {
        final StringBuilder sb = new StringBuilder();

        sb.append("(");
        int i = 0;
        for (Object item : collection) {
            if (i++ != 0) {
                sb.append(",");
            }
            sb.append(item instanceof String ? String.format("'%s'", item) : item);
        }
        sb.append(")");


        return sb.toString();
    }

    private String joinCriteria(Stream<Criteria> criteria) {
        return criteria.map(it -> it.isChain() ? joinCriteria(it.stream()) : joinCriteriaSet(it.setStream().collect(Collectors.toList())))
                .collect(Collectors.joining(" AND "));
    }

    private String joinCriteriaSet(List<CriteriaSet> criteriaSets) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < criteriaSets.size(); i++) {
            CriteriaSet criteria = criteriaSets.get(i);

            if (i != 0) {
                sb.append(" ").append(criteria.getAndOr().name()).append(" ");
            }

            if (criteriaSets.size() > 1) {
                sb.append("(");
            }

            sb.append(getCriteriaSet(criteriaSets.get(i)));

            if (criteriaSets.size() > 1) {
                sb.append(")");
            }
        }
        return sb.toString();
    }

    private String getCriteriaSet(CriteriaSet criteriaSet) {
        final QProperty propertyHolder = criteriaSet.getProperty();
        final String property = getFormatProperty(propertyHolder);
        final Object value = criteriaSet.getValue();
        final Object min = criteriaSet.getMin();
        final Object max = criteriaSet.getMax();
        switch (criteriaSet.getOperation()) {
            case EQUAL:
                return value instanceof String ? String.format("%s = '%s'", property, value) : String.format("%s = %s", property, value.toString());
            case NOT_EQUAL:
                return value instanceof String ? String.format("%s != '%s'", property, value) : String.format("%s != %s", property, value.toString());
            case GREATER_THAN:
                return value instanceof String ? String.format("%s > '%s'", property, value) : String.format("%s > %s", property, value.toString());
            case GREATER_EQUAL_THAN:
                return value instanceof String ? String.format("%s >= '%s'", property, value) : String.format("%s >= %s", property, value.toString());
            case LESS_THAN:
                return value instanceof String ? String.format("%s < '%s'", property, value) : String.format("%s < %s", property, value.toString());
            case LESS_EQUAL_THAN:
                return value instanceof String ? String.format("%s <= '%s'", property, value) : String.format("%s <= %s", property, value.toString());
            case IN:
                return String.format("%s IN %s", property, buildInString((Collection<?>) value));
            case NOT_IN:
                return String.format("%s NOT IN %s", property, buildInString((Collection<?>) value));
            case START_WITH:
                return String.format("%s LIKE '%s%%'", property, value.toString());
            case NOT_START_WITH:
                return String.format("%s NOT LIKE '%s%%'", property, value.toString());
            case END_WITH:
                return String.format("%s LIKE '%%%s'", property, value.toString());
            case NOT_END_WITH:
                return String.format("%s NOT LIKE '%%%s'", property, value.toString());
            case CONTAINS:
                return String.format("%s LIKE '%%%s%%'", property, value.toString());
            case NOT_CONTAINS:
                return String.format("%s NOT LIKE '%%%s%%'", property, value.toString());
            case LIKE:
                return String.format("%s LIKE '%s'", property, value.toString());
            case NOT_LIKE:
                return String.format("%s NOT LIKE '%s'", property, value.toString());
            case NULL:
                return String.format("%s IS NULL", property);
            case NOT_NULL:
                return String.format("%s IS NOT NULL", property);
            case BETWEEN:
                return value instanceof String ? String.format("%s BETWEEN '%s' AND '%s'", property, min, max) : String.format("%s BETWEEN %s AND %s", property, min, max);
            case NOT_BETWEEN:
                return value instanceof String ? String.format("%s NOT BETWEEN '%s' AND '%s'", property, min, max) : String.format("%s BETWEEN %s AND %s", property, min, max);

            case BEFORE:
                return String.format("%s < '%s'", property, dateFormat.format(value));
            case AFTER:
                return String.format("%s > '%s'", property, dateFormat.format(value));
            case DATE_BEFORE:
                return String.format("DATE(%s) < '%s'", property, dateFormat.format(value));
            case DATE_AFTER:
                return String.format("DATE(%s) > '%s'", property, dateFormat.format(value));
            case DATE_BETWEEN:
                return String.format("DATE(%s) BETWEEN '%s' AND '%s'", property, dateFormat.format(min), dateFormat.format(max));
            case NOT_DATE_BETWEEN:
                return String.format("DATE(%s) NOT BETWEEN '%s' AND '%s'", property, dateFormat.format(min), dateFormat.format(max));

            default:
                return "";
        }

    }


}
