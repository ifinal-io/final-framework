package com.ilikly.finalframework.data.query;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-18 14:49:06
 * @since 1.0
 */
public class SingleCriterionImpl<T> implements SingleCriterion<T> {
    private final QProperty property;
    private final String operation;
    private final T value;

    private SingleCriterionImpl(BuilderImpl<T> builder) {
        this.property = builder.property;
        this.operation = builder.operation;
        this.value = builder.value;
    }

    public static <T> Builder<T> builder() {
        return new BuilderImpl<>();
    }

    @Override
    public QProperty property() {
        return property;
    }

    @Override
    public String operation() {
        return operation;
    }

    @Override
    public T value() {
        return value;
    }

    private static class BuilderImpl<T> implements SingleCriterion.Builder<T> {
        private QProperty property;
        private String operation;
        private T value;

        private BuilderImpl() {
        }

        @Override
        public Builder<T> property(QProperty property) {
            this.property = property;
            return this;
        }

        @Override
        public Builder<T> operation(String operation) {
            this.operation = operation;
            return this;
        }

        @Override
        public Builder<T> value(T value) {
            this.value = value;
            return this;
        }

        @Override
        public Criterion build() {
            return new SingleCriterionImpl<>(this);
        }
    }
}
