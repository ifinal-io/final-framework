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
public class CollectionCriterionImpl<T> extends AbsCriterion<T> implements CollectionCriterion<T> {
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
        return null;
    }

    @Override
    public String getColumn() {
        return getPropertyColumn(property, functions);
    }

    @Override
    public Collection<T> getValue() {
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
        public SingleCriterion.Builder<Collection<T>> typeHandler(Class<? extends TypeHandler> typeHandler) {
            return null;
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
