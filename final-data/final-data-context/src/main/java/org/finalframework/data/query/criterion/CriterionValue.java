package org.finalframework.data.query.criterion;


import org.apache.ibatis.type.TypeHandler;
import org.finalframework.core.Assert;
import org.finalframework.data.query.QProperty;
import org.finalframework.data.query.SqlKeyWords;
import org.finalframework.data.query.criterion.function.operation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2020-03-02 09:11:59
 * @since 1.0
 */
public class CriterionValue<T> {
    private final T value;
    private final Class<?> javaType;
    private final Class<? extends TypeHandler<?>> typeHandler;
    private final Collection<FunctionOperation> functions;

    public CriterionValue(Builder<T> builder) {
        this.value = builder.value;
        this.javaType = builder.javaType;
        this.typeHandler = builder.typeHandler;
        this.functions = Assert.isEmpty(builder.functions) ? Collections.emptyList() : Collections.unmodifiableCollection(builder.functions);
    }

    public static <T> Builder<T> builder(T value) {
        return new Builder<>(value);
    }

    public T getValue() {
        return value;
    }

    public Class<?> getJavaType() {
        return javaType;
    }

    public Class<? extends TypeHandler<?>> getTypeHandler() {
        return typeHandler;
    }

    public Collection<FunctionOperation> getFunctions() {
        return functions;
    }

    public String getSql() {
        return getSqlExpression(null);
    }

    private boolean isProperty() {
        return this.value instanceof QProperty;
    }

    public String getSqlExpression(String expression) {
        String value;
        if (Assert.nonNull(expression)) {
            value = expression;
        } else if (isProperty()) {
            QProperty<?> property = (QProperty<?>) this.value;
            value = SqlKeyWords.contains(property.getColumn().toLowerCase()) ?
                    String.format("`%s`", property.getColumn()) : property.getColumn();
        } else {
            value = this.value.toString();
        }

        if (Assert.nonNull(expression)) {
            StringBuilder sb = new StringBuilder();
            sb.append("#{").append(value);

            if (Assert.nonNull(javaType)) {
                sb.append(" ,javaType=").append(javaType.getCanonicalName());
            }

            if (Assert.nonNull(typeHandler)) {
                sb.append(" ,typeHandler=").append(typeHandler.getCanonicalName());
            }

            sb.append("}");

            value = sb.toString();
        }

        if (Assert.nonEmpty(functions)) {
            for (FunctionOperation function : functions) {
                Class<?> type = getFunctionOperationType(function);
                FunctionOperationExpression functionOperationExpression = FunctionOperationRegistry.getInstance().getCriterionOperation(function.operation(), type);
                value = functionOperationExpression.expression(value, function);
            }
        }
        return value;
    }


    private Class<?> getFunctionOperationType(FunctionOperation operation) {
        if (operation instanceof SingleFunctionOperation) {
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

    public static class Builder<T> implements org.finalframework.core.Builder<CriterionValue<T>> {
        private final T value;
        private final Collection<FunctionOperation> functions = new ArrayList<>();
        private Class<?> javaType;
        private Class<? extends TypeHandler<?>> typeHandler;

        private Builder(T value) {
            this.value = value;
        }

        public Builder<T> javaType(Class<?> javaType) {
            this.javaType = javaType;
            return this;
        }

        public Builder<T> typeHandler(Class<? extends TypeHandler<?>> typeHandler) {
            this.typeHandler = typeHandler;
            return this;
        }

        public Builder<T> addFunction(FunctionOperation function) {
            this.functions.add(function);
            return this;
        }

        public Builder<T> functions(Collection<FunctionOperation> functions) {
            if (Assert.nonEmpty(functions)) {
                this.functions.addAll(functions);
            }
            return this;
        }


        @Override
        public CriterionValue<T> build() {
            return new CriterionValue<>(this);
        }
    }
}

