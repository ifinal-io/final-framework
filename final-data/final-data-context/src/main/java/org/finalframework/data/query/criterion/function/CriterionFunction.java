package org.finalframework.data.query.criterion.function;

import java.util.function.Function;
import org.finalframework.data.query.criterion.CriterionType;
import org.finalframework.data.query.operation.Operation;

/**
 * @author likly
 * @version 1.0
 * @date 2020-06-01 11:08:54
 * @since 1.0
 */
public interface CriterionFunction extends Operation {

    default CriterionType getType() {
        return CriterionType.FUNCTION;
    }

    default String name() {
        return getOperation().name();
    }

    Operation getOperation();

    default CriterionFunction map(Function<CriterionFunction, CriterionFunction> mapper) {
        return mapper.apply(this);
    }
}
