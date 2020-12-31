package org.ifinal.finalframework.data.query.criterion;

import java.util.Optional;
import java.util.function.Function;
import org.apache.ibatis.type.TypeHandler;
import org.ifinal.finalframework.data.query.SqlNode;
import org.ifinal.finalframework.data.query.criterion.function.CriterionFunction;
import org.springframework.lang.NonNull;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@SuppressWarnings("rawtypes")
public interface CriterionValue<V> extends SqlNode {

    @SuppressWarnings("unchecked")
    static <V> CriterionValue<V> from(V value) {

        return value instanceof CriterionValue ? (CriterionValue<V>) value : new CriterionValueImpl<>(value);
    }

    CriterionValue<V> javaType(Class<?> javaType);

    CriterionValue<V> typeHandler(Class<? extends TypeHandler<?>> typeHandler);

    V getValue();

    Class<?> getJavaType();

    Class<? extends TypeHandler> getTypeHandler();

    default CriterionFunction apply(Function<CriterionValue<V>, CriterionFunction> mapper) {
        return mapper.apply(this);
    }

    @Override
    default void apply(@NonNull StringBuilder parent, @NonNull String expression) {

        V value = getValue();

        if (value instanceof SqlNode) {
            ((SqlNode) value).apply(parent, String.format("%s.value", expression));
        } else {
            parent.append("#{").append(expression).append(".value");

            if (getJavaType() != null) {
                parent.append(",javaType=")
                    .append(Optional.ofNullable(getJavaType()).map(Class::getCanonicalName).orElse(""));
            }

            if (getTypeHandler() != null) {
                parent.append(",typeHandler=")
                    .append(Optional.ofNullable(getTypeHandler()).map(Class::getCanonicalName).orElse(""));
            }

            parent.append("}");
        }

    }

}

