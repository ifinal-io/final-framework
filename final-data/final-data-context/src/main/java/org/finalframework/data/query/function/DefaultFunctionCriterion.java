package org.finalframework.data.query.function;


import org.finalframework.data.query.DefaultFunctionOperator;
import org.finalframework.data.query.FunctionCriterion;
import org.springframework.lang.NonNull;

/**
 * @author likly
 * @version 1.0
 * @date 2019-11-24 16:44:28
 * @since 1.0
 */
public interface DefaultFunctionCriterion {

    static FunctionCriterion and(@NonNull Number value) {
        return new SingleFunctionCriterion<>(DefaultFunctionOperator.AND, value);
    }

    static FunctionCriterion or(@NonNull Number value) {
        return new SingleFunctionCriterion<>(DefaultFunctionOperator.OR, value);
    }

    static FunctionCriterion xor(@NonNull Number value) {
        return new SingleFunctionCriterion<>(DefaultFunctionOperator.XOR, value);
    }

    static FunctionCriterion not() {
        return new SimpleFunctionCriterion(DefaultFunctionOperator.NOT);
    }
}

