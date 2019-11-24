package org.finalframework.data.query.criterion.operation;

import org.finalframework.core.Assert;
import org.finalframework.data.query.FunctionCriterion;
import org.finalframework.data.query.FunctionOperationRegistry;
import org.finalframework.data.query.QProperty;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-18 13:42:22
 * @since 1.0
 */
public abstract class AbsCriterion<T> {

    private static final Set<String> SQL_KEYS = new HashSet<>();

    static {
        SQL_KEYS.add("key");
    }

    protected <T> T warpValue(Class<T> type, Object value) {
        if (Date.class == type) {

        } else if (LocalDateTime.class == type) {

        }

        return (T) value;
    }

    @SuppressWarnings("unchecked")
    protected String getPropertyColumn(QProperty property, Collection<FunctionCriterion> functions) {
        String column = SQL_KEYS.contains(property.getColumn().toLowerCase()) ?
                String.format("`%s`", property.getColumn()) : property.getColumn();

//        final Class<?> javaType = property.isCollectionLike() ? property.getComponentType() : property.getType();
        final Class<?> javaType = property.getType();
        ;

        if (Assert.nonEmpty(functions)) {
            for (FunctionCriterion function : functions) {
                column = FunctionOperationRegistry.getInstance().getCriterionOperation(function.operator(), javaType).format(column, function);
            }
        }

        return column;


    }
}
