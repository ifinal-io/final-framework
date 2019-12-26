package org.finalframework.data.query.criterion;

import org.apache.ibatis.type.TypeHandler;
import org.finalframework.core.Assert;
import org.finalframework.data.query.QProperty;
import org.finalframework.data.query.criterion.operator.CriterionOperator;
import org.finalframework.data.query.function.expression.FunctionExpression;
import org.finalframework.data.query.function.operation.*;

import java.util.*;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-18 13:42:22
 * @since 1.0
 */
public abstract class AbsCriterion<T> implements Criterion {

    private static final Set<String> SQL_KEYS = new HashSet<>();

    private final CriterionProperty<?> property;
    private final Collection<FunctionOperation> functions = new ArrayList<>();
    private final CriterionOperator operator;
    private final Class<? extends TypeHandler<?>> typeHandler;

    @SuppressWarnings("unchecked")
    public AbsCriterion(AbsBuilder builder) {
        this.property = new CriterionProperty(builder.property, builder.functions);
        this.operator = builder.operator;
        this.typeHandler = builder.typeHandler;
    }

    static {
        SQL_KEYS.add("key");
    }

    @Override
    public CriterionProperty<?> getProperty() {
        return this.property;
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
    public Class<? extends TypeHandler<?>> getTypeHandler() {
        return this.typeHandler;
    }

    public String getColumn() {
        return getPropertyColumn(property.getProperty(), property.getFunctions());
    }

    @Override
    public Criterion contact(String prefix, String suffix) {
        this.functions.add(new DoubleFunctionOperation<>(FunctionExpression.CONCAT, prefix, suffix));
        return this;
    }

    public String getFunctionValue(String expression) {
        return getFunctionValue(expression, this.functions);
    }

    @SuppressWarnings("unchecked")
    protected String getPropertyColumn(QProperty property, Collection<? extends FunctionOperation> functions) {
        String column = SQL_KEYS.contains(property.getColumn().toLowerCase()) ?
                String.format("`%s`", property.getColumn()) : property.getColumn();

        return getFunctionValue(column, functions);

    }

    protected String getFunctionValue(String expression, Collection<? extends FunctionOperation> functions) {
        String value = expression;
        if (Assert.nonEmpty(functions)) {
            for (FunctionOperation function : functions) {
                Class<?> type = getFunctionOperationType(function);
                FunctionOperationExpression functionOperationExpression = FunctionOperationRegistry.getInstance().getCriterionOperation(function.operator(), type);
                value = functionOperationExpression.expression(value, function);
            }
        }
        return value;
    }

    private Class<?> getFunctionOperationType(FunctionOperation operation) {
        if (operation instanceof SimpleFunctionOperation) {
            return Object.class;
        } else if (operation instanceof SingleFunctionOperation) {
            SingleFunctionOperation singleFunctionOperation = (SingleFunctionOperation) operation;
            Object value = singleFunctionOperation.value();

            if (value instanceof List) {
                return ((List) value).get(0).getClass();
            } else {
                return value.getClass();
            }
        }

        return Object.class;
    }

    @SuppressWarnings("unchecked")
    public static abstract class AbsBuilder<T, R extends Builder> implements Builder<T, R> {
        private QProperty<?> property;
        private Collection<FunctionOperation> functions = new ArrayList<>();
        private CriterionOperator operator;
        private Class<? extends TypeHandler<?>> typeHandler;

        @Override
        public R property(QProperty<?> property, Collection<FunctionOperation> functions) {
            this.property = property;
            if (functions != null) {
                this.functions.addAll(functions);
            }
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
