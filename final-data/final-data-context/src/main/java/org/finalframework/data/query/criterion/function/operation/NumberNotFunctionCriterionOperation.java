package org.finalframework.data.query.criterion.function.operation;


import org.finalframework.data.query.criterion.FunctionCriterion;
import org.finalframework.data.query.criterion.FunctionCriterionOperation;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-28 21:21:33
 * @since 1.0
 */

@FunctionOperation(
        value = FunctionOperation.NOT,
        types = {
                byte.class, Byte.class,
                short.class, Short.class,
                int.class, Integer.class,
                long.class, Long.class,
                float.class, Float.class,
                double.class, Double.class
        }
)
public class NumberNotFunctionCriterionOperation<T extends Number> implements FunctionCriterionOperation<FunctionCriterion> {

    @Override
    public String format(String column, FunctionCriterion criterion) {
        return String.format("~ %s", column);
    }
}
