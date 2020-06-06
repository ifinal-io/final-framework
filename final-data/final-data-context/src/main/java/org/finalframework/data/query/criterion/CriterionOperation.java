package org.finalframework.data.query.criterion;

import org.finalframework.data.query.SqlNode;
import org.finalframework.data.query.operation.Operation;
import org.springframework.lang.NonNull;

/**
 * @author likly
 * @version 1.0
 * @date 2020-06-06 20:27:43
 * @since 1.0
 */
public interface CriterionOperation extends Criterion, Operation, SqlNode {
    @Override
    default boolean isChain() {
        return false;
    }

    @Override
    default String name() {
        return getOperation().name();
    }

    @NonNull
    Operation getOperation();
}
