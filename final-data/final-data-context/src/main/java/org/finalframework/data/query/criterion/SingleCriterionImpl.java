package org.finalframework.data.query.criterion;

import org.finalframework.data.query.criterion.operator.CriterionOperator;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-18 14:49:06
 * @since 1.0
 */
public class SingleCriterionImpl<T> extends AbsCriterion<T> implements SingleCriterion<T> {
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

    public String getFunctionValue() {
        String expression = getTypeHandler() == null ? "#{criterion.value}" : String.format("#{criterion.value, typeHandler=%s}", getTypeHandler().getCanonicalName());
        if (CriterionOperator.IN.getName().equals(getOperator().getName()) || CriterionOperator.IN.getName().equals(getOperator().getName())) {
            expression = getTypeHandler() == null ? "#{value}" : String.format("#{value, typeHandler=%s}", getTypeHandler().getCanonicalName());
        }
        return getFunctionValue(expression);
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
