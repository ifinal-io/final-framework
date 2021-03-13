package org.ifinal.finalframework.data.query.operation;

import org.springframework.lang.NonNull;

import org.ifinal.finalframework.data.query.criterion.function.CriterionFunction;
import org.ifinal.finalframework.data.query.criterion.function.SimpleCriterionFunction;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public final class MathOperations {

    private MathOperations() {
    }

    public static CriterionFunction max(final @NonNull Object target) {
        return new SimpleCriterionFunction(target, MathOperation.MAX);
    }

    public static CriterionFunction min(final @NonNull Object target) {
        return new SimpleCriterionFunction(target, MathOperation.MIN);
    }

    public static CriterionFunction avg(final @NonNull Object target) {
        return new SimpleCriterionFunction(target, MathOperation.AVG);
    }

    public static CriterionFunction sum(final @NonNull Object target) {
        return new SimpleCriterionFunction(target, MathOperation.SUM);
    }

}
