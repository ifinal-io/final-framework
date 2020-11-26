package org.ifinal.finalframework.data.query.criterion;

import org.apache.ibatis.type.TypeHandler;
import org.ifinal.finalframework.data.query.operation.Operation;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public abstract class SimpleCriterionImpl<T> implements SimpleCriterion {

    private final Object target;
    private final Operation operation;

    @SuppressWarnings("unchecked")
    public SimpleCriterionImpl(AbsBuilder builder) {
        this.target = builder.target;
        this.operation = builder.operation;
    }

    @Override
    public Object getTarget() {
        return this.target;
    }


    @Override
    public Operation getOperation() {
        return this.operation;
    }

    public String getCriterionTarget() {
        return ((CriterionValueImpl) getTarget()).getSql();
    }


    @SuppressWarnings("unchecked")
    public static abstract class AbsBuilder<T, R extends Builder> implements Builder<T, R> {
        private Object target;
        private Operation operation;
        private Class<? extends TypeHandler<?>> typeHandler;

        @Override
        public R target(Object target) {
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
