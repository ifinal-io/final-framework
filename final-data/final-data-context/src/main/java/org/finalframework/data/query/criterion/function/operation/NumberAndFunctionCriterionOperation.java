package org.finalframework.data.query.criterion.function.operation;


import org.finalframework.data.query.criterion.FunctionCriterionOperation;
import org.finalframework.data.query.criterion.function.SingleFunctionCriterion;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-28 21:21:33
 * @see NumberOrFunctionCriterionOperation
 * @see NumberXorFunctionCriterionOperation
 * @see NumberNotFunctionCriterionOperation
 * @since 1.0
 */
@FunctionOperation(
        value = FunctionOperation.AND,
        types = {
                byte.class, Byte.class,
                short.class, Short.class,
                int.class, Integer.class,
                long.class, Long.class,
                float.class, Float.class,
                double.class, Double.class
        }
)
public class NumberAndFunctionCriterionOperation<T> implements FunctionCriterionOperation<SingleFunctionCriterion<T>> {

    @Override
    public String format(String column, SingleFunctionCriterion<T> criterion) {
        return String.format("%s & %s", column, criterion.value());
    }
}
