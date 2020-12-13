package org.ifinal.finalframework.data.query.operation;

import org.ifinal.finalframework.data.query.criterion.function.CriterionFunction;
import org.ifinal.finalframework.data.query.criterion.function.SimpleCriterionFunction;
import org.springframework.lang.NonNull;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public final class MathOperations {

    private MathOperations() {
    }

    public static CriterionFunction max(@NonNull Object target) {
        return new SimpleCriterionFunction(target, MathOperation.MAX);
    }

    public static CriterionFunction min(@NonNull Object target) {
        return new SimpleCriterionFunction(target, MathOperation.MIN);
    }

    public static CriterionFunction avg(@NonNull Object target) {
        return new SimpleCriterionFunction(target, MathOperation.AVG);
    }

    public static CriterionFunction sum(@NonNull Object target) {
        return new SimpleCriterionFunction(target, MathOperation.SUM);
    }

}
