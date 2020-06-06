package org.finalframework.data.query.criterion;


import org.apache.ibatis.type.TypeHandler;
import org.finalframework.data.query.QProperty;
import org.finalframework.data.query.SqlNode;
import org.finalframework.data.query.criterion.function.CriterionFunction;
import org.finalframework.data.query.operation.function.FunctionOperation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Function;

/**
 * @author likly
 * @version 1.0
 * @date 2020-03-02 09:11:59
 * @since 1.0
 */
public interface CriterionValue<V> extends SqlNode {

    @SuppressWarnings("unchecked")
    static <V> CriterionValue<V> from(V value) {
        return value instanceof CriterionValue ? (CriterionValue<V>) value : new CriterionValueImpl<>(value);
    }


    CriterionValue<V> javaType(Class<?> javaType);

    CriterionValue<V> typeHandler(Class<? extends TypeHandler<?>> typeHandler);

    default CriterionFunction apply(Function<CriterionValue<V>, CriterionFunction> mapper) {
        return mapper.apply(this);
    }

    V getValue();

    Class<?> getJavaType();

    Class<? extends TypeHandler<?>> getTypeHandler();

    @Override
    default void apply(Node parent, String expression) {
        final Document document = parent.getOwnerDocument();

        final V value = getValue();

        if (value instanceof SqlNode) {
            ((SqlNode) value).apply(parent, String.format("%s.value", expression));
        } else if (value instanceof Iterable) {
            final Element foreach = document.createElement("foreach");

            parent.appendChild(foreach);
        } else {
            final StringBuilder builder = new StringBuilder();
            builder.append("#{").append(expression).append(".value");

            if (getJavaType() != null) {
                builder.append(",javaType=").append(Optional.ofNullable(getJavaType()).map(Class::getCanonicalName).orElse(""));
            }

            if (getTypeHandler() != null) {
                builder.append(",typeHandler=").append(Optional.ofNullable(getTypeHandler()).map(Class::getCanonicalName).orElse(""));
            }

            builder.append("}");
            parent.appendChild(document.createCDATASection(builder.toString()));
        }


    }
}

