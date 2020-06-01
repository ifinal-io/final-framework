package org.finalframework.data.query.criterion;


import org.apache.ibatis.type.TypeHandler;
import org.finalframework.data.query.QProperty;
import org.finalframework.data.query.criterion.function.CriterionFunction;
import org.finalframework.data.query.operation.function.FunctionOperation;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.function.Function;

/**
 * @author likly
 * @version 1.0
 * @date 2020-03-02 09:11:59
 * @since 1.0
 */
public interface CriterionValue<V> {

    @SuppressWarnings("unchecked")
    static <V> CriterionValue<V> from(V value) {
        return value instanceof CriterionValue ? (CriterionValue<V>) value : new CriterionValueImpl<>(value);
    }

    default CriterionType getType() {
        if (getValue() instanceof QProperty) {
            return CriterionType.PROPERTY;
        }

        if (getValue() instanceof Collection || getValue() instanceof Array) {
            return CriterionType.COLLECTION;
        }

        return CriterionType.VALUE;
    }


    CriterionValue<V> javaType(Class<?> javaType);

    CriterionValue<V> typeHandler(Class<? extends TypeHandler<?>> typeHandler);

    default CriterionFunction apply(Function<CriterionValue<V>, CriterionFunction> mapper) {
        return mapper.apply(this);
    }

    V getValue();

    Class<?> getJavaType();

    Class<? extends TypeHandler<?>> getTypeHandler();

}

