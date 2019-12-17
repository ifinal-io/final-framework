package org.finalframework.data.query.criterion.function;


import org.finalframework.data.query.criterion.FunctionCriterion;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-28 23:18:52
 * @since 1.0
 */
public class SimpleFunctionCriterion implements FunctionCriterion {
    private final String operator;

    public SimpleFunctionCriterion(String operator) {
        this.operator = operator;
    }

    @Override
    public String operator() {
        return operator;
    }
}
