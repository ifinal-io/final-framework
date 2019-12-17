package org.finalframework.data.query.criterion.function.operation;


import org.finalframework.data.query.criterion.operator.DefaultFunctionOperator;
import org.finalframework.data.query.criterion.FunctionCriterion;
import org.finalframework.data.query.criterion.FunctionOperation;
import org.finalframework.data.query.criterion.operator.FunctionOperator;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-28 21:21:33
 * @since 1.0
 */
public abstract class NotFunctionOperation<T> implements FunctionOperation<FunctionCriterion> {

    @Override
    public FunctionOperator operator() {
        return DefaultFunctionOperator.NOT;
    }

}
