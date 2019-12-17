package org.finalframework.data.query.criterion;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-28 20:59:43
 * @since 1.0
 */
@FunctionalInterface
public interface FunctionCriterionOperation<T extends FunctionCriterion> {
    String format(String column, T criterion);
}
