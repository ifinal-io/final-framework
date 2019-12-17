package org.finalframework.data.query.criterion.function.operation;


import org.finalframework.data.entity.enums.IEnum;
import org.finalframework.data.query.criterion.FunctionCriterionOperation;
import org.finalframework.data.query.criterion.function.SingleFunctionCriterion;

/**
 * @author likly
 * @version 1.0
 * @date 2019-12-17 16:06:50
 * @since 1.0
 */
@FunctionOperation(
        value = FunctionOperation.OR,
        types = {IEnum.class}
)
public class IEnumOrFunctionCriterionOperation<T extends IEnum> implements FunctionCriterionOperation<SingleFunctionCriterion<T>> {
    @Override
    public String format(String column, SingleFunctionCriterion<T> criterion) {
        return String.format("%s | %s", column, String.valueOf(criterion.value().getCode()));
    }
}

