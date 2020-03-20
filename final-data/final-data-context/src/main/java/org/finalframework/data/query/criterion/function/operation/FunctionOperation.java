package org.finalframework.data.query.criterion.function.operation;


import org.finalframework.data.query.criterion.function.FunctionOperator;
import org.springframework.lang.NonNull;

/**
 * 函数运算
 *
 * @author likly
 * @version 1.0
 * @date 2019-03-28 21:16:43
 * @see SimpleFunctionOperation
 * @see SingleFunctionOperation
 * @see FunctionOperator
 * @since 1.0
 */
public interface FunctionOperation {

    @NonNull
    FunctionOperator operator();
}
