package com.ilikly.finalframework.data.query.criterion;

import com.ilikly.finalframework.data.query.Criterion;
import com.ilikly.finalframework.data.query.CriterionOperator;
import com.ilikly.finalframework.data.query.QProperty;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-18 14:49:06
 * @since 1.0
 */
public class BetweenCriterionImpl<T> implements BetweenCriterion<T> {
    private final QProperty property;
    private final CriterionOperator operator;
    private final T min;
    private final T max;

    private BetweenCriterionImpl(BuilderImpl<T> builder) {
        this.property = builder.property;
        this.operator = builder.operator;
        this.min = builder.min;
        this.max = builder.max;
    }

    public static <T> Builder<T> builder() {
        return new BuilderImpl<>();
    }

    @Override
    public QProperty property() {
        return property;
    }

    @Override
    public CriterionOperator operator() {
        return operator;
    }

    @Override
    public T min() {
        return min;
    }

    @Override
    public T max() {
        return max;
    }

    private static class BuilderImpl<T> implements BetweenCriterion.Builder<T> {
        private QProperty property;
        private CriterionOperator operator;
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
        public Builder<T> operator(CriterionOperator operator) {
            this.operator = operator;
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
