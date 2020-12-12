package org.ifinal.finalframework.data.query.criterion;


import org.ifinal.finalframework.data.query.operation.Operation;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.ifinal.finalframework.data.query.operation.Operation.CompareOperation;
import static org.ifinal.finalframework.data.query.operation.Operation.CompareOperation.IN;
import static org.ifinal.finalframework.data.query.operation.Operation.CompareOperation.NOT_IN;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class SingleCriterionImpl<T> extends SimpleCriterionImpl implements SingleCriterion<T> {

    private static final Set<Operation> OPERATOR_IN = new HashSet<>(Arrays.asList(
            IN,
            NOT_IN
    ));

    private final T value;

    private SingleCriterionImpl(final BuilderImpl<T> builder) {

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

    @SuppressWarnings("unused")
    public String getCriterionValue() {
        String expression = OPERATOR_IN.contains(getOperation()) ? "value" : "criterion.value";
        return ((CriterionValueImpl<?>) CriterionValue.from(value)).getSqlExpression(expression);
    }

    @Override
    public String toString() {
        final Operation operation = getOperation();
        if (operation instanceof CompareOperation) {
            CompareOperation compareOperation = (CompareOperation) operation;
            switch (compareOperation) {
                case EQUAL:
                    return String.format(" %s = %s", getCriterionTarget(), CriterionValue.from(getValue()));
                case NOT_BETWEEN:
                    return String.format(" %s != %s", getCriterionTarget(), CriterionValue.from(getValue()));
                default:
                    throw new IllegalArgumentException(compareOperation.name());
            }
        }
        return "";

    }

    private static class BuilderImpl<T> extends AbsBuilder<SingleCriterion<T>, SingleCriterion.Builder<T>>
            implements SingleCriterion.Builder<T> {
        private T value;

        @Override
        public SingleCriterion.Builder<T> value(final T value) {

            this.value = value;
            return this;
        }

        @Override
        public SingleCriterion<T> build() {
            return new SingleCriterionImpl<>(this);
        }
    }
}
