

package org.finalframework.data.query.operation;


import org.finalframework.data.query.criterion.function.CriterionFunction;
import org.finalframework.data.query.criterion.function.SimpleCriterionFunction;
import org.finalframework.data.query.criterion.function.SingleCriterionFunction;
import org.springframework.lang.NonNull;

/**
 * @author likly
 * @version 1.0
 * @date 2020-07-14 15:23:16
 * @since 1.0
 */
public interface LogicOperations {
    static CriterionFunction and(@NonNull Object target, @NonNull Object value) {
        return new SingleCriterionFunction(target, LogicOperation.AND, value);
    }

    static CriterionFunction or(@NonNull Object target, @NonNull Object value) {
        return new SingleCriterionFunction(target, LogicOperation.OR, value);
    }

    static CriterionFunction not(@NonNull Object target) {
        return new SimpleCriterionFunction(target, LogicOperation.NOT);
    }

    static CriterionFunction xor(@NonNull Object target, @NonNull Object value) {
        return new SingleCriterionFunction(target, LogicOperation.XOR, value);
    }
}

