package org.finalframework.data.query.criterion.function.operation;

import org.finalframework.data.query.operation.Operation;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-28 20:59:43
 * @since 1.0
 */
public interface FunctionOperationExpression<T extends FunctionOperation> {

    Operation operation();

    /**
     * @param target    目标
     * @param operation 函数运算
     */
    String expression(String target, T operation);
}
