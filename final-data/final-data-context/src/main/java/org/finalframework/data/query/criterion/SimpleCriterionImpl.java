package org.finalframework.data.query.criterion;

import org.apache.ibatis.type.TypeHandler;
import org.finalframework.data.query.criterion.function.operation.FunctionOperation;
import org.finalframework.data.query.criterion.function.operation.SimpleFunctionOperation;
import org.finalframework.data.query.operation.DateOperation;
import org.finalframework.data.query.operation.Operation;
import org.finalframework.data.query.operation.StringOperation;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-18 13:42:22
 * @since 1.0
 */
public abstract class SimpleCriterionImpl<T> implements SimpleCriterion {

    private final CriterionValue<?, ?> target;
    private final Operation operation;

    @SuppressWarnings("unchecked")
    public SimpleCriterionImpl(AbsBuilder builder) {
        this.target = builder.target;
        this.operation = builder.operation;
    }

    @Override
    public CriterionValue<?, ?> getTarget() {
        return this.target;
    }


    @Override
    public Operation getOperation() {
        return this.operation;
    }

    public String getCriterionTarget() {
        return ((CriterionValueImpl) getTarget()).getSql();
    }

    @Override
    public SimpleCriterion contact(String prefix, String suffix) {
        return this;
    }

    @Override
    public SimpleCriterion date() {
        return this;
    }

    @SuppressWarnings("unchecked")
    public static abstract class AbsBuilder<T, R extends Builder> implements Builder<T, R> {
        private CriterionValue<?, ?> target;
        private Collection<FunctionOperation> functions = new ArrayList<>();
        private Operation operation;
        private Class<? extends TypeHandler<?>> typeHandler;

        @Override
        public R target(CriterionValue<?, ?> target) {
            this.target = target;
            return (R) this;
        }


        @Override
        public R operation(Operation operation) {
            this.operation = operation;
            return (R) this;
        }

    }
}
