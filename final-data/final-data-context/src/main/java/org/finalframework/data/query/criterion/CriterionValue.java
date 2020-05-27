package org.finalframework.data.query.criterion;


import org.apache.ibatis.type.TypeHandler;
import org.finalframework.data.query.criterion.function.operation.FunctionOperation;

import java.util.Collection;

/**
 * @author likly
 * @version 1.0
 * @date 2020-03-02 09:11:59
 * @since 1.0
 */
public interface CriterionValue<V, R extends CriterionValue<?, ?>> {

    @SuppressWarnings("unchecked")
    static <V> CriterionValue<V, CriterionValue<V, ?>> from(V value) {
        return value instanceof CriterionValue ? (CriterionValue<V, CriterionValue<V, ?>>) value : new CriterionValueImpl(value);
    }

    R javaType(Class<?> javaType);

    R typeHandler(Class<? extends TypeHandler<?>> typeHandler);

    R apply(CriterionFunction function);

    V getValue();

    Class<?> getJavaType();

    Class<? extends TypeHandler<?>> getTypeHandler();

    Collection<FunctionOperation> getFunctions();

}

