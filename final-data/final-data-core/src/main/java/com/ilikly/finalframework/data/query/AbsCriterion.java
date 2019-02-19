package com.ilikly.finalframework.data.query;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-18 14:49:06
 * @since 1.0
 */
public abstract class AbsCriterion<T> implements Criterion<T> {
    private final QProperty property;
    private final String operation;

    private AbsCriterion(AbsBuilder<T> builder) {
        this.property = builder.property;
        this.operation = builder.operation;
    }

    @Override
    public QProperty property() {
        return property;
    }

    @Override
    public String operation() {
        return operation;
    }

    public abstract static class AbsBuilder<T> implements Builder<T> {
        private QProperty property;
        private String operation;
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

    }
}
