package org.finalframework.data.query.function.operation;


import org.finalframework.data.query.FunctionCriterion;
import org.finalframework.data.query.function.BitFunctionOperations;
import org.finalframework.data.query.function.SingleFunctionCriterion;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-28 21:51:57
 * @since 1.0
 */
public class NumberFunctionOperations<T extends Number> extends BaseFunctionOperations<T> implements BitFunctionOperations<T> {

    public NumberFunctionOperations(Class<T> type) {
        super(type);
        register(and());
        register(or());
        register(xor());
        register(not());
    }

    @Override
    public AndFunctionOperation<T> and() {
        return new AndFunctionOperation<T>() {
            @Override
            public String format(String column, SingleFunctionCriterion<T> criterion) {
                return String.format("%s & %s", column, String.valueOf(criterion.value()));
            }
        };
    }


    @Override
    public OrFunctionOperation<T> or() {
        return new OrFunctionOperation<T>() {
            @Override
            public String format(String column, SingleFunctionCriterion<T> criterion) {
                return String.format("%s | %s", column, String.valueOf(criterion.value()));
            }
        };
    }


    @Override
    public XorFunctionOperation<T> xor() {
        return new XorFunctionOperation<T>() {
            @Override
            public String format(String column, SingleFunctionCriterion<T> criterion) {
                return String.format("%s ^ %s", column, String.valueOf(criterion.value()));
            }
        };
    }


    @Override
    public NotFunctionOperation<T> not() {
        return new NotFunctionOperation<T>() {
            @Override
            public String format(String column, FunctionCriterion criterion) {
                return String.format("~ %s", column);
            }
        };
    }
}
