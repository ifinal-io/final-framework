package org.finalframework.data.query.criterion;


import org.finalframework.data.query.operation.Operation;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-18 14:49:06
 * @since 1.0
 */
public class SingleCriterionImpl<T> extends SimpleCriterionImpl<T> implements SingleCriterion<T> {

    private static final Set<Operation> OPERATOR_IN = new HashSet<>(Arrays.asList(
            Operation.CompareOperation.IN,
            Operation.CompareOperation.NOT_IN
    ));

    private final T value;

    private SingleCriterionImpl(BuilderImpl<T> builder) {
        super(builder);
        this.value = builder.value;
    }

    public static <T> SingleCriterion.Builder<T> builder() {
        return new BuilderImpl<>();
    }

    @Override
    public T getValue() {
        return value;
    }

    public String getCriterionValue() {
        String expression = OPERATOR_IN.contains(getOperation()) ? "value" : "criterion.value";
        return CriterionValue.builder(value)
                .functions(getFunctions())
                .typeHandler(getTypeHandler())
                .build().getSqlExpression(expression);
    }

    private static class BuilderImpl<T> extends AbsBuilder<SingleCriterion<T>, SingleCriterion.Builder<T>>
            implements SingleCriterion.Builder<T> {
        private T value;

        @Override
        public SingleCriterion.Builder<T> value(T value) {
            this.value = value;
            return this;
        }

        @Override
        public SingleCriterion<T> build() {
            return new SingleCriterionImpl<>(this);
        }
    }
}
