package org.finalframework.data.query.criterion.operation;

import org.finalframework.data.query.QProperty;

import java.util.HashSet;
import java.util.Set;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-18 13:42:22
 * @since 1.0
 */
public abstract class AbsCriterionOperation<T> {

    private static final Set<String> SQL_KEYS = new HashSet<>();

    static {
        SQL_KEYS.add("key");
    }

    protected String getPropertyColumn(QProperty property) {
        return SQL_KEYS.contains(property.getColumn().toLowerCase()) ?
                String.format("`%s`",property.getColumn()) : property.getColumn();
    }
}
