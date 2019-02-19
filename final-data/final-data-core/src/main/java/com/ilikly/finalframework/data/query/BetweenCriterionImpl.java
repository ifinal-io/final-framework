package com.ilikly.finalframework.data.query;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-18 14:49:06
 * @since 1.0
 */
public class BetweenCriterionImpl<T extends Comparable<T>> implements BetweenCriterion<T> {
    private final QProperty property;
    private final String operation;
    private final T min;
    private final T max;

    private BetweenCriterionImpl(BuilderImpl<T> builder) {
        this.property = builder.property;
        this.operation = builder.operation;
        this.min = builder.min;
        this.max = builder.max;
    }

    public static <T extends Comparable<T>> Builder<T> builder() {
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
    public T min() {
        return min;
    }

    @Override
    public T max() {
        return max;
    }

    private static class BuilderImpl<T extends Comparable<T>> implements BetweenCriterion.Builder<T> {
        private QProperty property;
        private String operation;
        private T min;
        private T max;

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
        public Builder<T> between(T min, T max) {
            this.min = min;
            this.max = max;
            return this;
        }


        @Override
        public Criterion build() {
            return new BetweenCriterionImpl<>(this);
        }
    }
}
