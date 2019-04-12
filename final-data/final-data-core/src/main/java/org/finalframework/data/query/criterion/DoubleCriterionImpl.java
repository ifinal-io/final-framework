package org.finalframework.data.query.criterion;

import org.finalframework.data.query.Criterion;
import org.finalframework.data.query.CriterionOperator;
import org.finalframework.data.query.FunctionCriterion;
import org.finalframework.data.query.QProperty;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-18 14:49:06
 * @since 1.0
 */
public class DoubleCriterionImpl<T> implements DoubleCriterion<T> {
    private final QProperty property;
    private final Collection<FunctionCriterion> functions;
    private final CriterionOperator operator;
    private final T min;
    private final T max;

    private DoubleCriterionImpl(BuilderImpl<T> builder) {
        this.property = builder.property;
        this.functions = builder.functions;
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
    public Collection<FunctionCriterion> functions() {
        return functions;
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

    private static class BuilderImpl<T> implements DoubleCriterion.Builder<T> {
        private QProperty property;
        private Collection<FunctionCriterion> functions = new ArrayList<>();
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
        public Builder<T> function(FunctionCriterion function) {
            this.functions.add(function);
            return this;
        }

        @Override
        public Builder<T> function(Collection<FunctionCriterion> functions) {
            this.functions.addAll(functions);
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
            return new DoubleCriterionImpl<>(this);
        }
    }
}
