package org.finalframework.data.query.function.operation;


import org.finalframework.data.query.DefaultFunctionOperator;
import org.finalframework.data.query.FunctionOperation;
import org.finalframework.data.query.FunctionOperator;
import org.finalframework.data.query.function.SingleFunctionCriterion;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-28 21:21:33
 * @since 1.0
 */
public abstract class XorFunctionOperation<T> implements FunctionOperation<SingleFunctionCriterion<T>> {

    @Override
    public FunctionOperator operator() {
        return DefaultFunctionOperator.XOR;
    }

}
