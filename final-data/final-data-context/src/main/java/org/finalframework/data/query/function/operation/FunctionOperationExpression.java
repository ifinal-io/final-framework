package org.finalframework.data.query.function.operation;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-28 20:59:43
 * @since 1.0
 */
@FunctionalInterface
public interface FunctionOperationExpression<T extends FunctionOperation> {
    /**
     * @param target    目标
     * @param operation 函数运算
     */
    String expression(String target, T operation);
}
