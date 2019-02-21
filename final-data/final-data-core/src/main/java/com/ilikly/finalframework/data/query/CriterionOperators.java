package com.ilikly.finalframework.data.query;

/**
 * @author likly
 * @version 1.0
 * @date 2019-02-20 09:31:57
 * @since 1.0
 */
public class CriterionOperators implements CriterionOperator {

    public static final CriterionOperator EQUAL = new CriterionOperators("EQUAL");
    public static final CriterionOperator NOT_EQUAL = new CriterionOperators("NOT_EQUAL");
    public static final CriterionOperator GREATER_THAN = new CriterionOperators("GREATER_THAN");
    public static final CriterionOperator GREATER_EQUAL_THAN = new CriterionOperators("GREATER_EQUAL_THAN");
    public static final CriterionOperator LESS_THAN = new CriterionOperators("LESS_THAN");
    public static final CriterionOperator LESS_EQUAL_THAN = new CriterionOperators("LESS_EQUAL_THAN");
    public static final CriterionOperator IN = new CriterionOperators("IN");
    public static final CriterionOperator NOT_IN = new CriterionOperators("NOT_IN");
    public static final CriterionOperator START_WITH = new CriterionOperators("START_WITH");
    public static final CriterionOperator NOT_START_WITH = new CriterionOperators("NOT_START_WITH");
    public static final CriterionOperator END_WITH = new CriterionOperators("END_WITH");
    public static final CriterionOperator NOT_END_WITH = new CriterionOperators("NOT_END_WITH");
    public static final CriterionOperator CONTAINS = new CriterionOperators("CONTAINS");
    public static final CriterionOperator NOT_CONTAINS = new CriterionOperators("NOT_CONTAINS");
    public static final CriterionOperator LIKE = new CriterionOperators("LIKE");
    public static final CriterionOperator NOT_LIKE = new CriterionOperators("NOT_LIKE");
    public static final CriterionOperator BEFORE = new CriterionOperators("BEFORE");
    public static final CriterionOperator AFTER = new CriterionOperators("AFTER");
    public static final CriterionOperator DATE_BEFORE = new CriterionOperators("DATE_BEFORE");
    public static final CriterionOperator DATE_AFTER = new CriterionOperators("DATE_AFTER");
    public static final CriterionOperator BETWEEN = new CriterionOperators("BETWEEN");
    public static final CriterionOperator NOT_BETWEEN = new CriterionOperators("NOT_BETWEEN");
    public static final CriterionOperator DATE_BETWEEN = new CriterionOperators("DATE_BETWEEN");
    public static final CriterionOperator NOT_DATE_BETWEEN = new CriterionOperators("NOT_DATE_BETWEEN");
    public static final CriterionOperator NULL = new CriterionOperators("NULL");
    public static final CriterionOperator NOT_NULL = new CriterionOperators("NOT_NULL");


    private final String name;

    public CriterionOperators(String name) {
        this.name = name;
    }

    @Override
    public String name() {
        return this.name;
    }
}
