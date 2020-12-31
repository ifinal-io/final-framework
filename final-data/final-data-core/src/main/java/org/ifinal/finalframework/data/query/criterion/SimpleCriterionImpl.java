package org.ifinal.finalframework.data.query.criterion;

import org.ifinal.finalframework.data.query.operation.Operation;
import org.springframework.lang.NonNull;

/**
 * SimpleCriterionImpl.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public abstract class SimpleCriterionImpl implements SimpleCriterion {

    private final Object target;

    private final Operation operation;

    protected SimpleCriterionImpl(final AbsBuilder<?, ?> builder) {

        this.target = builder.target;
        this.operation = builder.operation;
    }

    @Override
    @NonNull
    public Object getTarget() {
        return this.target;
    }

    @Override
    @NonNull
    public Operation getOperation() {
        return this.operation;
    }

    public String getCriterionTarget() {
        return ((CriterionValueImpl<?>) getTarget()).getSql();
    }

    /**
     * AbsBuilder.
     *
     * @param <T> type
     * @param <R> return type
     */
    @SuppressWarnings("unchecked")
    public abstract static class AbsBuilder<T, R extends Builder> implements Builder<T, R> {

        private Object target;

        private Operation operation;

        @Override
        @NonNull
        public R target(final @NonNull Object target) {

            this.target = target;
            return (R) this;
        }

        @Override
        @NonNull
        public R operation(final @NonNull Operation operation) {

            this.operation = operation;
            return (R) this;
        }

    }

}
