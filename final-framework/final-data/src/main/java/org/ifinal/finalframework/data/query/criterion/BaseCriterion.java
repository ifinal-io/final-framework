package org.ifinal.finalframework.data.query.criterion;


import org.ifinal.finalframework.data.query.QProperty;
import org.ifinal.finalframework.data.query.SqlNode;
import org.springframework.lang.NonNull;

import java.io.Serializable;
import java.lang.reflect.Array;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class BaseCriterion implements Serializable {

    protected void applyValueCriterion(@NonNull StringBuilder sql, Object value, String prefix, String suffix, String expression) {

        sql.append("<trim");

        if (prefix != null) {
            sql.append(" prefix=\"").append(prefix).append("\"");
        }

        if (suffix != null) {
            sql.append(" suffix=\"").append(suffix).append("\"");
        }

        sql.append(">");

        if (value instanceof SqlNode) {
            ((SqlNode) value).apply(sql, expression);
        } else if (value instanceof QProperty) {
            sql.append(((QProperty<?>) value).getColumn());
        } else if (value instanceof Iterable || value instanceof Array) {
            sql.append("<foreach collection=\"").append(expression).append("\" item=\"item\" separator=\",\">#{item}</foreach>");
        } else {
            sql.append("#{").append(expression).append("}");
        }

        sql.append("</trim>");

    }
}

