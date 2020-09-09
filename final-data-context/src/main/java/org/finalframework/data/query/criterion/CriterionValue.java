

package org.finalframework.data.query.criterion;


import org.apache.ibatis.type.TypeHandler;
import org.finalframework.data.query.SqlNode;
import org.finalframework.data.query.criterion.function.CriterionFunction;

import java.util.Optional;
import java.util.function.Function;

/**
 * @author likly
 * @version 1.0
 * @date 2020-03-02 09:11:59
 * @since 1.0
 */
public interface CriterionValue<V> extends SqlNode {

    @SuppressWarnings("unchecked")
    static <V> CriterionValue<V> from(V value) {
        return value instanceof CriterionValue ? (CriterionValue<V>) value : new CriterionValueImpl<>(value);
    }


    CriterionValue<V> javaType(Class<?> javaType);

    CriterionValue<V> typeHandler(Class<? extends TypeHandler<?>> typeHandler);

    default CriterionFunction apply(Function<CriterionValue<V>, CriterionFunction> mapper) {
        return mapper.apply(this);
    }

    V getValue();

    Class<?> getJavaType();

    Class<? extends TypeHandler<?>> getTypeHandler();

    @Override
    default void apply(StringBuilder parent, String expression) {

        final V value = getValue();

        if (value instanceof SqlNode) {
            ((SqlNode) value).apply(parent, String.format("%s.value", expression));
        } else if (value instanceof Iterable) {
        } else {
            parent.append("#{").append(expression).append(".value");

            if (getJavaType() != null) {
                parent.append(",javaType=").append(Optional.ofNullable(getJavaType()).map(Class::getCanonicalName).orElse(""));
            }

            if (getTypeHandler() != null) {
                parent.append(",typeHandler=").append(Optional.ofNullable(getTypeHandler()).map(Class::getCanonicalName).orElse(""));
            }

            parent.append("}");
        }


    }
}

