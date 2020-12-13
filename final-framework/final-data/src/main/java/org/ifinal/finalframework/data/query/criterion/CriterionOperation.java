package org.ifinal.finalframework.data.query.criterion;

import org.ifinal.finalframework.data.query.SqlNode;
import org.ifinal.finalframework.data.query.operation.Operation;
import org.springframework.lang.NonNull;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface CriterionOperation extends Criterion, Operation, SqlNode {

    @Override
    default String name() {
        return getOperation().name();
    }

    @NonNull
    Operation getOperation();

}
