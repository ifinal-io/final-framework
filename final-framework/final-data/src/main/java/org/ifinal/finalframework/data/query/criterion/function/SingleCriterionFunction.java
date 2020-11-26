package org.ifinal.finalframework.data.query.criterion.function;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.ifinal.finalframework.data.query.operation.Operation;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Getter
@AllArgsConstructor
public class SingleCriterionFunction implements CriterionFunction {
    private final Object target;
    private final Operation operation;
    private final Object value;

    @Override
    public void apply(StringBuilder sql, String expression) {

    }
}

