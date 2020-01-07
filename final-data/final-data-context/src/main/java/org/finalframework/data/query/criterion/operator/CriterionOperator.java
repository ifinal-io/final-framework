package org.finalframework.data.query.criterion.operator;

import org.springframework.lang.NonNull;

/**
 * @author likly
 * @version 1.0
 * @date 2019-02-20 09:30:13
 * @since 1.0
 */
public interface CriterionOperator {

    CriterionOperator EQUAL = new DefaultCriterionOperator("EQUAL");
    CriterionOperator NOT_EQUAL = new DefaultCriterionOperator("NOT_EQUAL");
    CriterionOperator GREATER_THAN = new DefaultCriterionOperator("GREATER_THAN");
    CriterionOperator GREATER_THAN_EQUAL = new DefaultCriterionOperator("GREATER_THAN_EQUAL");
    CriterionOperator LESS_THAN = new DefaultCriterionOperator("LESS_THAN");
    CriterionOperator LESS_THAN_EQUAL = new DefaultCriterionOperator("LESS_THAN_EQUAL");

    CriterionOperator IN = new DefaultCriterionOperator("IN");
    CriterionOperator NOT_IN = new DefaultCriterionOperator("NOT_IN");

    CriterionOperator LIKE = new DefaultCriterionOperator("LIKE");
    CriterionOperator NOT_LIKE = new DefaultCriterionOperator("NOT_LIKE");

    CriterionOperator BETWEEN = new DefaultCriterionOperator("BETWEEN");
    CriterionOperator NOT_BETWEEN = new DefaultCriterionOperator("NOT_BETWEEN");

    CriterionOperator NULL = new DefaultCriterionOperator("NULL");
    CriterionOperator NOT_NULL = new DefaultCriterionOperator("NOT_NULL");

    @NonNull
    String getName();

}
