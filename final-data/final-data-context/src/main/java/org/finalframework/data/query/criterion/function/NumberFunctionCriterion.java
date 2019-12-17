package org.finalframework.data.query.criterion.function;


import org.finalframework.data.query.criterion.operator.DefaultFunctionOperator;
import org.finalframework.data.query.criterion.FunctionCriterion;
import org.springframework.lang.NonNull;

/**
 * @author likly
 * @version 1.0
 * @date 2019-11-24 16:44:28
 * @since 1.0
 */
public interface NumberFunctionCriterion {

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

    static FunctionCriterion min() {
        return new SimpleFunctionCriterion(DefaultFunctionOperator.MIN);
    }

    static FunctionCriterion max() {
        return new SimpleFunctionCriterion(DefaultFunctionOperator.MAX);
    }

    static FunctionCriterion sum() {
        return new SimpleFunctionCriterion(DefaultFunctionOperator.SUM);
    }

    static FunctionCriterion avg() {
        return new SimpleFunctionCriterion(DefaultFunctionOperator.AVG);
    }

}

