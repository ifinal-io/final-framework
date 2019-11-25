package org.finalframework.data.query;

import lombok.Getter;
import org.finalframework.core.Assert;
import org.springframework.lang.NonNull;

/**
 * @author likly
 * @version 1.0
 * @date 2019-02-20 09:31:57
 * @since 1.0
 */
public class DefaultCriterionOperator implements CriterionOperator {

    public static final CriterionOperator EQUAL = new DefaultCriterionOperator("EQUAL");
    public static final CriterionOperator NOT_EQUAL = new DefaultCriterionOperator("NOT_EQUAL");
    public static final CriterionOperator GREATER_THAN = new DefaultCriterionOperator("GREATER_THAN");
    public static final CriterionOperator GREATER_THAN_EQUAL = new DefaultCriterionOperator("GREATER_THAN_EQUAL");
    public static final CriterionOperator LESS_THAN = new DefaultCriterionOperator("LESS_THAN");
    public static final CriterionOperator LESS_THAN_EQUAL = new DefaultCriterionOperator("LESS_THAN_EQUAL");

    public static final CriterionOperator IN = new DefaultCriterionOperator("IN");
    public static final CriterionOperator NOT_IN = new DefaultCriterionOperator("NOT_IN");
    public static final CriterionOperator START_WITH = new DefaultCriterionOperator("START_WITH");
    public static final CriterionOperator NOT_START_WITH = new DefaultCriterionOperator("NOT_START_WITH");
    public static final CriterionOperator END_WITH = new DefaultCriterionOperator("END_WITH");
    public static final CriterionOperator NOT_END_WITH = new DefaultCriterionOperator("NOT_END_WITH");
    public static final CriterionOperator CONTAINS = new DefaultCriterionOperator("CONTAINS");
    public static final CriterionOperator NOT_CONTAINS = new DefaultCriterionOperator("NOT_CONTAINS");
    public static final CriterionOperator LIKE = new DefaultCriterionOperator("LIKE");
    public static final CriterionOperator NOT_LIKE = new DefaultCriterionOperator("NOT_LIKE");
    public static final CriterionOperator BEFORE = new DefaultCriterionOperator("BEFORE");
    public static final CriterionOperator AFTER = new DefaultCriterionOperator("AFTER");
    public static final CriterionOperator DATE_EQUAL = new DefaultCriterionOperator("DATE_EQUAL");
    public static final CriterionOperator NOT_DATE_EQUAL = new DefaultCriterionOperator("NOT_DATE_EQUAL");
    public static final CriterionOperator DATE_BEFORE = new DefaultCriterionOperator("DATE_BEFORE");
    public static final CriterionOperator DATE_AFTER = new DefaultCriterionOperator("DATE_AFTER");
    public static final CriterionOperator BETWEEN = new DefaultCriterionOperator("BETWEEN");
    public static final CriterionOperator NOT_BETWEEN = new DefaultCriterionOperator("NOT_BETWEEN");
    public static final CriterionOperator DATE_BETWEEN = new DefaultCriterionOperator("DATE_BETWEEN");
    public static final CriterionOperator NOT_DATE_BETWEEN = new DefaultCriterionOperator("NOT_DATE_BETWEEN");
    public static final CriterionOperator NULL = new DefaultCriterionOperator("NULL");
    public static final CriterionOperator NOT_NULL = new DefaultCriterionOperator("NOT_NULL");


    @Getter
    private final String name;

    public DefaultCriterionOperator(@NonNull String name) {
        Assert.isBlank(name, "name is blank");
        this.name = name;
    }

    @Override
    @NonNull
    public String name() {
        return this.name;
    }


    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CriterionOperator) {
            return name.equals(((CriterionOperator) obj).name());
        }
        return false;
    }

    @Override
    public String toString() {
        return name;
    }
}
