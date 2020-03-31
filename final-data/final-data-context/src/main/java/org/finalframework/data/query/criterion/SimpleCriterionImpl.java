package org.finalframework.data.query.criterion;

import org.apache.ibatis.type.TypeHandler;
import org.finalframework.data.query.criterion.function.operation.DoubleFunctionOperation;
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

    private final CriterionValue<?> target;
    private final Collection<FunctionOperation> functions = new ArrayList<>();
    private final Operation operation;
    private Class<? extends TypeHandler<?>> typeHandler;

    @SuppressWarnings("unchecked")
    public SimpleCriterionImpl(AbsBuilder builder) {
        this.target = builder.target;
        this.operation = builder.operation;
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
    public Operation getOperation() {
        return this.operation;
    }

    @Override
    public SimpleCriterion setTypeHandler(Class<? extends TypeHandler<?>> typeHandler) {
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
    public SimpleCriterion contact(String prefix, String suffix) {
        this.functions.add(new DoubleFunctionOperation<>(StringOperation.CONCAT, prefix, suffix));
        return this;
    }

    @Override
    public SimpleCriterion date() {
        this.functions.add(new SimpleFunctionOperation(DateOperation.DATE));
        return this;
    }

    @SuppressWarnings("unchecked")
    public static abstract class AbsBuilder<T, R extends Builder> implements Builder<T, R> {
        private CriterionValue<?> target;
        private Collection<FunctionOperation> functions = new ArrayList<>();
        private Operation operation;
        private Class<? extends TypeHandler<?>> typeHandler;

        @Override
        public R target(CriterionValue<?> target) {
            this.target = target;
            return (R) this;
        }


        @Override
        public R operation(Operation operation) {
            this.operation = operation;
            return (R) this;
        }

        @Override
        public R typeHandler(Class<? extends TypeHandler<?>> typeHandler) {
            this.typeHandler = typeHandler;
            return (R) this;
        }
    }
}
