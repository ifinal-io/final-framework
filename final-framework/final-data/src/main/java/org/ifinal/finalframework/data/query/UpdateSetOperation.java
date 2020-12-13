package org.ifinal.finalframework.data.query;

import org.ifinal.finalframework.data.query.operation.Operation;
import org.springframework.lang.NonNull;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface UpdateSetOperation extends Operation, SqlNode {

    @Override
    default String name() {
        return getOperation().name();
    }

    @NonNull
    Operation getOperation();

}
