package org.ifinal.finalframework.data.query.criterion;


import lombok.Data;
import org.apache.ibatis.type.TypeHandler;
import org.ifinal.finalframework.annotation.data.SqlKeyWords;
import org.ifinal.finalframework.data.query.QProperty;
import org.ifinal.finalframework.util.Asserts;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Data
class CriterionValueImpl<T> implements CriterionValue<T> {
    private final T value;
    private Class<?> javaType;
    private Class<? extends TypeHandler<?>> typeHandler;

    public CriterionValueImpl(final T value) {

        this.value = value;
    }

    @Override
    public CriterionValue<T> javaType(final Class<?> javaType) {

        this.javaType = javaType;
        return this;
    }

    @Override
    public CriterionValue<T> typeHandler(final Class<? extends TypeHandler<?>> typeHandler) {

        this.typeHandler = typeHandler;
        return this;
    }

    public String getSql() {
        return getSqlExpression(null);
    }

    private boolean isProperty() {
        return this.value instanceof QProperty;
    }

    public String getSqlExpression(final String expression) {

        String criterionValue;
        if (Asserts.nonNull(expression)) {
            criterionValue = expression;
        } else if (isProperty()) {
            QProperty<?> property = (QProperty<?>) this.value;
            criterionValue = SqlKeyWords.contains(property.getColumn().toLowerCase()) ?
                    String.format("`%s`", property.getColumn()) : property.getColumn();
        } else {
            criterionValue = this.value instanceof String ? String.format("'%s'", this.value) : this.value.toString();
        }

        if (Asserts.nonNull(expression)) {
            StringBuilder sb = new StringBuilder();
            sb.append("#{").append(criterionValue);

            if (Asserts.nonNull(javaType)) {
                sb.append(" ,javaType=").append(javaType.getCanonicalName());
            }

            if (Asserts.nonNull(typeHandler)) {
                sb.append(" ,typeHandler=").append(typeHandler.getCanonicalName());
            }

            sb.append("}");

            criterionValue = sb.toString();
        }

        return criterionValue;
    }

    @Override
    public String toString() {
        return getSql();
    }

}

