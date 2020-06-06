package org.finalframework.data.query.criterion.function;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.finalframework.data.query.operation.Operation;
import org.w3c.dom.Node;

/**
 * @author likly
 * @version 1.0
 * @date 2020-06-01 13:25:57
 * @since 1.0
 */
@Getter
@AllArgsConstructor
public class SingleCriterionFunction implements CriterionFunction {
    private final Object target;
    private final Operation operation;
    private final Object value;

    @Override
    public void apply(Node parent, String expression) {

    }
}

