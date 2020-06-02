package org.finalframework.data.query.criterion.function;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.finalframework.data.query.operation.Operation;

/**
 * @author likly
 * @version 1.0
 * @date 2020-06-01 13:22:51
 * @since 1.0
 */
@Getter
@AllArgsConstructor
public class SimpleCriterionFunction implements CriterionFunction {
    private final Object value;
    private final Operation operation;
}

