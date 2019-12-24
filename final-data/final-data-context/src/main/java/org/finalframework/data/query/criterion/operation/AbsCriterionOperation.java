package org.finalframework.data.query.criterion.operation;

import org.finalframework.core.Assert;
import org.finalframework.data.query.criterion.FunctionCriterion;
import org.finalframework.data.query.criterion.FunctionCriterionOperation;
import org.finalframework.data.query.criterion.FunctionOperationRegistry;
import org.finalframework.data.query.QProperty;

import java.util.Collection;
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
        SQL_KEYS.add("order");
        SQL_KEYS.add("group");
    }

    @SuppressWarnings("unchecked")
    protected String getPropertyColumn(QProperty property, Collection<? extends FunctionCriterion> functions) {
        String column = SQL_KEYS.contains(property.getColumn().toLowerCase()) ?
                String.format("`%s`", property.getColumn()) : property.getColumn();

//        final Class<?> javaType = property.isCollectionLike() ? property.getComponentType() : property.getType();
        final Class<?> javaType = property.getType();
        ;

        if (Assert.nonEmpty(functions)) {
            for (FunctionCriterion function : functions) {
                FunctionCriterionOperation functionCriterionOperation = FunctionOperationRegistry.getInstance().getCriterionOperation(function.operator(), javaType);
                column = functionCriterionOperation.format(column, function);
            }
        }

        return column;


    }
}
