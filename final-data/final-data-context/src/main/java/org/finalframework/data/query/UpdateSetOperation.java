package org.finalframework.data.query;

import org.finalframework.data.query.operation.Operation;
import org.springframework.lang.NonNull;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-25 11:31
 * @since 1.0
 */
public interface UpdateSetOperation extends Operation, SqlNode {

    @Override
    default String name() {
        return getOperation().name();
    }

    @NonNull
    Operation getOperation();


}
