package org.finalframework.data.query.criterion.function;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.finalframework.data.query.QProperty;
import org.finalframework.data.query.SqlNode;
import org.finalframework.data.query.operation.Operation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * @author likly
 * @version 1.0
 * @date 2020-06-01 13:22:51
 * @since 1.0
 */
@Getter
@AllArgsConstructor
public class SimpleCriterionFunction implements CriterionFunction {
    private final Object value;
    private final Operation operation;

    @Override
    public void apply(Node parent, String expression) {
        final Document document = parent.getOwnerDocument();

        final Element trim = document.createElement("trim");
        trim.setAttribute("prefix", name() + "(");
        trim.setAttribute("suffix", ")");

        if (value instanceof SqlNode) {
            ((SqlNode) value).apply(trim, String.format("%s.value", expression));
        } else if (value instanceof QProperty) {
            trim.appendChild(document.createCDATASection(String.format("${%s.value.column}", expression)));
        } else {
            trim.appendChild(document.createCDATASection(String.format("#{%s.value}", expression)));
        }

        parent.appendChild(trim);


    }
}

