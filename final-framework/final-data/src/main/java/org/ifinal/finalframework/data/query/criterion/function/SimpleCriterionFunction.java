package org.ifinal.finalframework.data.query.criterion.function;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.ifinal.finalframework.data.query.QProperty;
import org.ifinal.finalframework.data.query.SqlNode;
import org.ifinal.finalframework.data.query.criterion.BaseCriterion;
import org.ifinal.finalframework.data.query.operation.Operation;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Getter
@AllArgsConstructor
public class SimpleCriterionFunction extends BaseCriterion implements CriterionFunction {
    private final Object value;
    private final Operation operation;

    @Override
    public void apply(StringBuilder sql, String expression) {

        sql.append(String.format("<trim prefix=\"%s(\" suffix=\")\">", name()));

        if (value instanceof SqlNode) {
            ((SqlNode) value).apply(sql, String.format("%s.value", expression));
        } else if (value instanceof QProperty) {
            sql.append(String.format("${%s.value.column}", expression));
        } else {
            sql.append(String.format("#{%s.value}", expression));
        }
        sql.append("</trim>");
    }
}

