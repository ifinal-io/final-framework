package org.finalframework.data.query.criterion;

import org.finalframework.data.query.QProperty;
import org.finalframework.data.query.SqlNode;
import org.finalframework.data.query.criterion.function.CriterionFunction;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.lang.reflect.Array;

/**
 * @author likly
 * @version 1.0
 * @date 2019-02-19 20:49:00
 * @since 1.0
 */
public interface SingleCriterion<T> extends SimpleCriterion {

    static <T> SingleCriterion.Builder<T> builder() {
        return SingleCriterionImpl.builder();
    }

    T getValue();

    @Override
    default void apply(Node parent, String expression) {
        final Document document = parent.getOwnerDocument();
        final Object target = getTarget();
        final T value = getValue();

        final Element targetElement = document.createElement("trim");

        if (target instanceof CriterionTarget) {
            ((CriterionTarget) target).apply(targetElement, String.format("%s.target", expression));
        } else if (target instanceof QProperty) {
            targetElement.appendChild(document.createTextNode(((QProperty) target).getColumn()));
        }

        parent.appendChild(targetElement);

        final Element valueElement = document.createElement("trim");
        valueElement.setAttribute("prefix", " = ");

        if (value instanceof SqlNode) {
            ((SqlNode) value).apply(valueElement, String.format("%s.value", expression));
        } else if (value instanceof Iterable || value instanceof Array) {

        } else {
            valueElement.appendChild(document.createTextNode(String.format("#{%s.value}", expression)));
        }

        parent.appendChild(valueElement);

    }

    interface Builder<T> extends SimpleCriterion.Builder<SingleCriterion<T>, Builder<T>> {

        Builder<T> value(T value);
    }

}
