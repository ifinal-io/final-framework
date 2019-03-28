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
public class CollectionCriterionImpl<T> implements CollectionCriterion<T> {
    private final QProperty property;
    private final Collection<FunctionCriterion> functions;
    private final CriterionOperator operator;
    private final Collection<T> value;

    private CollectionCriterionImpl(BuilderImpl<T> builder) {
        this.property = builder.property;
        this.functions = builder.functions;
        this.operator = builder.operator;
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
    public Collection<FunctionCriterion> functions() {
        return functions;
    }

    @Override
    public CriterionOperator operator() {
        return operator;
    }

    @Override
    public Collection<T> value() {
        return value;
    }

    private static class BuilderImpl<T> implements CollectionCriterion.Builder<T> {
        private QProperty property;
        private Collection<FunctionCriterion> functions = new ArrayList<>();
        private CriterionOperator operator;
        private Collection<T> value;

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
