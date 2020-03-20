package org.finalframework.data.query.criterion;

import org.apache.ibatis.type.TypeHandler;
import org.finalframework.data.query.criterion.function.FunctionOperator;
import org.finalframework.data.query.criterion.function.operation.*;

import java.util.*;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-18 13:42:22
 * @since 1.0
 */
public abstract class AbsCriterion<T> implements Criterion {

    private final CriterionValue<?> target;
    private final Collection<FunctionOperation> functions = new ArrayList<>();
    private final CriterionOperator operator;
    private Class<? extends TypeHandler<?>> typeHandler;

    @SuppressWarnings("unchecked")
    public AbsCriterion(AbsBuilder builder) {
        this.target = builder.target;
        this.operator = builder.operator;
        this.typeHandler = builder.typeHandler;
    }

    @Override
    public CriterionValue<?> getTarget() {
        return this.target;
    }

    @Override
    public Collection<FunctionOperation> getFunctions() {
        return this.functions;
    }

    @Override
    public CriterionOperator getOperator() {
        return this.operator;
    }

    @Override
    public Criterion setTypeHandler(Class<? extends TypeHandler<?>> typeHandler) {
        this.typeHandler = typeHandler;
        return this;
    }

    @Override
    public Class<? extends TypeHandler<?>> getTypeHandler() {
        return this.typeHandler;
    }

    public String getCriterionTarget() {
        return getTarget().getSql();
    }

    @Override
    public Criterion contact(String prefix, String suffix) {
        this.functions.add(new DoubleFunctionOperation<>(FunctionOperator.CONCAT, prefix, suffix));
        return this;
    }

    @Override
    public Criterion date() {
        this.functions.add(new SimpleFunctionOperation(FunctionOperator.DATE));
        return this;
    }

    @SuppressWarnings("unchecked")
    public static abstract class AbsBuilder<T, R extends Builder> implements Builder<T, R> {
        private CriterionValue<?> target;
        private Collection<FunctionOperation> functions = new ArrayList<>();
        private CriterionOperator operator;
        private Class<? extends TypeHandler<?>> typeHandler;

        @Override
        public R target(CriterionValue<?> target) {
            this.target = target;
            return (R) this;
        }


        @Override
        public R operator(CriterionOperator operator) {
            this.operator = operator;
            return (R) this;
        }

        @Override
        public R typeHandler(Class<? extends TypeHandler<?>> typeHandler) {
            this.typeHandler = typeHandler;
            return (R) this;
        }
    }
}
