package cn.com.likly.finalframework.mybatis.provider;

import cn.com.likly.finalframework.data.domain.Criteria;
import cn.com.likly.finalframework.data.domain.Query;
import cn.com.likly.finalframework.data.mapping.holder.EntityHolder;
import cn.com.likly.finalframework.data.mapping.holder.PropertyHolder;
import cn.com.likly.finalframework.data.provider.Provider;
import cn.com.likly.finalframework.mybatis.handler.TypeHandlerRegistry;
import cn.com.likly.finalframework.util.Assert;
import lombok.NonNull;
import org.apache.ibatis.type.TypeHandler;

import java.util.Collection;
import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-19 10:23
 * @since 1.0
 */
public abstract class AbsProvider<T> implements Provider<String> {

    private final TypeHandlerRegistry typeHandlerRegistry;

    public AbsProvider(TypeHandlerRegistry typeHandlerRegistry) {
        this.typeHandlerRegistry = typeHandlerRegistry;
    }

    String getTable(@NonNull EntityHolder<T> entity) {
        return entity.getTable();
    }

    String getJavaTypeAndTypeHandler(PropertyHolder property) {

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
        if (Assert.nonEmpty(query.getCriteria())) {
            sb.append(" WHERE ").append(getCriteria(Criteria.of(query.getCriteria())));
        }

        return sb.toString();
    }


    private String getCriteria(Criteria criteria) {
        if (Assert.isEmpty(criteria.getCriteriaChain())) {
            final PropertyHolder propertyHolder = criteria.getProperty();
            final String property = getFormatProperty(propertyHolder);
            final Object value = criteria.getValue();
            final Object min = criteria.getMin();
            final Object max = criteria.getMax();
            switch (criteria.getOperation()) {
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
                case LIKE:
                    return String.format("%s LIKE '%%%s%%'", property, value.toString());
                case NOT_LIKE:
                    return String.format("%s NOT LIKE '%%%s%%'", property, value.toString());
                case NULL:
                    return String.format("%s IS NULL", property);
                case NOT_NULL:
                    return String.format("%s IS NOT NULL", property);
                case BETWEEN:
                    return value instanceof String ? String.format("%s BETWEEN '%s' AND '%s'", property, min, max) : String.format("%s BETWEEN %s AND %s", property, min, max);
                case NOT_BETWEEN:
                    return value instanceof String ? String.format("%s NOT BETWEEN '%s' AND '%s'", property, min, max) : String.format("%s BETWEEN %s AND %s", property, min, max);
                default:
                    return "";
            }
        } else {
            return join(criteria.getCriteriaChain());
        }

    }

    private String getFormatProperty(PropertyHolder property) {
        return String.format("%s.%s", property.getTable(), property.getColumn());
    }

    protected abstract PropertyHolder getPropertyHolder(String property);

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

    private String join(List<Criteria> criteriaChain) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < criteriaChain.size(); i++) {
            Criteria criteria = criteriaChain.get(i);

            if (i != 0) {
                sb.append(" ").append(criteria.getAndOr().name()).append(" ");
            }

            if (Assert.nonEmpty(criteria.getCriteriaChain()) && criteria.getCriteriaChain().size() > 1) {
                sb.append("(");
            }

            sb.append(getCriteria(criteriaChain.get(i)));

            if (Assert.nonEmpty(criteria.getCriteriaChain()) && criteria.getCriteriaChain().size() > 1) {
                sb.append(")");
            }
        }
        return sb.toString();
    }


}
