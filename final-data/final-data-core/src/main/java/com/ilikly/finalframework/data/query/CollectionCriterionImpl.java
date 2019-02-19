package com.ilikly.finalframework.data.query;

import java.util.Collection;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-18 14:49:06
 * @since 1.0
 */
public class CollectionCriterionImpl<T> implements CollectionCriterion<T> {
    private final QProperty property;
    private final String operation;
    private final Collection<T> value;

    private CollectionCriterionImpl(BuilderImpl<T> builder) {
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
    public Collection<T> value() {
        return value;
    }

    private static class BuilderImpl<T> implements CollectionCriterion.Builder<T> {
        private QProperty property;
        private String operation;
        private Collection<T> value;

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
        public Builder<T> value(Collection<T> value) {
            this.value = value;
            return this;
        }

        @Override
        public Criterion build() {
            return new CollectionCriterionImpl<>(this);
        }
    }
}
