package org.finalframework.data.query.criterion;


import lombok.Data;
import org.apache.ibatis.type.TypeHandler;
import org.finalframework.core.Assert;
import org.finalframework.data.annotation.SqlKeyWords;
import org.finalframework.data.query.QProperty;
import org.finalframework.data.query.operation.function.Function;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author likly
 * @version 1.0
 * @date 2020-03-02 09:11:59
 * @since 1.0
 */
@Data
public class CriterionValueImpl<T, V> implements CriterionTarget<T, V>, Serializable {
    private static final long serialVersionUID = -5904183635896162713L;
    private final T value;
    private final Collection<Function> functions = new ArrayList<>();
    private Class<?> javaType;
    private Class<? extends TypeHandler<?>> typeHandler;

    public CriterionValueImpl(T value) {
        this.value = value;
    }

    @Override
    public CriterionTarget<T, V> javaType(Class<?> javaType) {
        this.javaType = javaType;
        return this;
    }

    @Override
    public CriterionTarget<T, V> typeHandler(Class<? extends TypeHandler<?>> typeHandler) {
        this.typeHandler = typeHandler;
        return this;
    }

    @Override
    public CriterionTarget<T, V> apply(Function function) {
        this.functions.add(function);
        return this;
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
            value = this.value instanceof String ? String.format("'%s'", this.value) : this.value.toString();
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
            for (Function function : functions) {
                value = function.apply(value);
            }
        }
        return value;
    }

    @Override
    public String toString() {
        return getSql();
    }

}

