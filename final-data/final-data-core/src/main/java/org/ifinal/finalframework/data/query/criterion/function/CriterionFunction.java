package org.ifinal.finalframework.data.query.criterion.function;

import java.util.function.UnaryOperator;
import org.ifinal.finalframework.data.query.SqlNode;
import org.ifinal.finalframework.data.query.operation.Operation;
import org.springframework.lang.NonNull;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface CriterionFunction extends Operation, SqlNode {

    @NonNull
    default String name() {
        return getOperation().name();
    }

    Operation getOperation();

    default CriterionFunction map(@NonNull UnaryOperator<CriterionFunction> mapper) {
        return mapper.apply(this);
    }

}
