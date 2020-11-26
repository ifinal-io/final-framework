package org.ifinal.finalframework.data.query.criterion.function;

import org.ifinal.finalframework.data.query.SqlNode;
import org.ifinal.finalframework.data.query.operation.Operation;

import java.util.function.Function;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface CriterionFunction extends Operation, SqlNode {

    default String name() {
        return getOperation().name();
    }

    Operation getOperation();

    default CriterionFunction map(Function<CriterionFunction, CriterionFunction> mapper) {
        return mapper.apply(this);
    }

}
