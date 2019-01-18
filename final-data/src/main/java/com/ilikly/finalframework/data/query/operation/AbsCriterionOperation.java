package com.ilikly.finalframework.data.query.operation;

import com.ilikly.finalframework.data.query.CriterionOperation;
import com.ilikly.finalframework.data.query.QProperty;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-18 13:42:22
 * @since 1.0
 */
public abstract class AbsCriterionOperation<T> implements CriterionOperation<T> {

    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:dd");


    @Override
    public String format(QProperty property, T value) {
        throw new IllegalArgumentException(String.format("property of %s is not support %s operation", getPropertyColumn(property), name()));
    }

    @Override
    public String format(QProperty property, T min, T max) {
        throw new IllegalArgumentException(String.format("property of %s is not support %s operation", getPropertyColumn(property), name()));
    }

    protected String getPropertyColumn(QProperty property) {
        return property.getColumn();
    }

    protected String buildInString(Collection<?> collection) {
        final StringBuilder sb = new StringBuilder();

        sb.append("(");
        int i = 0;
        for (Object item : collection) {
            if (i++ != 0) {
                sb.append(",");
            }
            sb.append(item instanceof String ? String.format("'%s'", item) : item);
        }
        sb.append(")");


        return sb.toString();
    }

    protected String format(Date date) {
        return dateFormat.format(date);
    }

}
