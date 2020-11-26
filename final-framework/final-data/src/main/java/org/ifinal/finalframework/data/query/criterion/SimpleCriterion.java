package org.ifinal.finalframework.data.query.criterion;

import org.ifinal.finalframework.data.query.operation.Operation;
import org.springframework.lang.NonNull;

/**
 * [标准，准则，规范，准据] 条件
 *
 * @author likly
 * @version 1.0.0
 * @see SingleCriterion
 * @since 1.0.0
 */
public interface SimpleCriterion<T> extends Criterion {

    @NonNull
    Object getTarget();

    @NonNull
    Operation getOperation();

    interface Builder<T, R extends Builder> extends org.ifinal.finalframework.util.Builder<T> {

        @NonNull
        R target(Object target);

        @NonNull
        R operation(@NonNull Operation operation);

    }

}
