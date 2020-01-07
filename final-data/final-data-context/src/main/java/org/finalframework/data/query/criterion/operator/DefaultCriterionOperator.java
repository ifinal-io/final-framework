package org.finalframework.data.query.criterion.operator;

import org.finalframework.core.Assert;
import org.springframework.lang.NonNull;

/**
 * @author likly
 * @version 1.0
 * @date 2019-02-20 09:31:57
 * @since 1.0
 */
public class DefaultCriterionOperator implements CriterionOperator {

    private final String name;

    public DefaultCriterionOperator(@NonNull String name) {
        Assert.isBlank(name, "name is blank");
        this.name = name;
    }

    @Override
    @NonNull
    public String getName() {
        return this.name;
    }


    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CriterionOperator) {
            return name.equals(((CriterionOperator) obj).getName());
        }
        return false;
    }

    @Override
    public String toString() {
        return name;
    }
}
