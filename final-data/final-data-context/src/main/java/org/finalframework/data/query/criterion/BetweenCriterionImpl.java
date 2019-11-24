package org.finalframework.data.query.criterion;

import org.apache.ibatis.type.TypeHandler;
import org.finalframework.data.query.Criterion;
import org.finalframework.data.query.CriterionOperator;
import org.finalframework.data.query.FunctionCriterion;
import org.finalframework.data.query.QProperty;
import org.finalframework.data.query.criterion.operation.AbsCriterion;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-18 14:49:06
 * @since 1.0
 */
public class BetweenCriterionImpl<T> extends AbsCriterion<T> implements BetweenCriterion<T> {
    private final QProperty property;
    private final Collection<FunctionCriterion> functions;
    private final CriterionOperator operator;
    private final Class<? extends TypeHandler> typeHandler;
    private final T min;
    private final T max;

    private BetweenCriterionImpl(BuilderImpl<T> builder) {
        this.property = builder.property;
        this.functions = builder.functions;
        this.operator = builder.operator;
        this.typeHandler = builder.typeHandler;
        this.min = builder.min;
        this.max = builder.max;
    }

    public static <T> Builder<T> builder() {
        return new BuilderImpl<>();
    }

    @Override
    public QProperty getProperty() {
        return property;
    }

    @Override
    public Collection<FunctionCriterion> getFunctions() {
        return functions;
    }

    @Override
    public CriterionOperator getOperator() {
        return operator;
    }

    @Override
    public Class<? extends TypeHandler> getTypeHandler() {
        return typeHandler;
    }

    @Override
    public String getColumn() {
        return getPropertyColumn(property, functions);
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
        private Collection<FunctionCriterion> functions = new ArrayList<>();
        private CriterionOperator operator;
        private Class<? extends TypeHandler> typeHandler;
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
        public Builder<T> typeHandler(Class<? extends TypeHandler> typeHandler) {
            this.typeHandler = typeHandler;
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
