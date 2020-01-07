package org.finalframework.data.query.criterion.operator;

import org.springframework.lang.NonNull;

/**
 * @author likly
 * @version 1.0
 * @date 2019-02-20 09:30:13
 * @since 1.0
 */
public interface CriterionOperator {

    /**
     * IS NULL
     */
    CriterionOperator NULL = new DefaultCriterionOperator("NULL");

    /**
     * IS NOT NULL
     */
    CriterionOperator NOT_NULL = new DefaultCriterionOperator("NOT_NULL");

    /**
     * =
     */
    CriterionOperator EQUAL = new DefaultCriterionOperator("EQUAL");

    /**
     * !=
     */
    CriterionOperator NOT_EQUAL = new DefaultCriterionOperator("NOT_EQUAL");

    /**
     * >
     */
    CriterionOperator GREATER_THAN = new DefaultCriterionOperator("GREATER_THAN");

    /**
     * >=
     */
    CriterionOperator GREATER_THAN_EQUAL = new DefaultCriterionOperator("GREATER_THAN_EQUAL");

    /**
     * <
     */
    CriterionOperator LESS_THAN = new DefaultCriterionOperator("LESS_THAN");

    /**
     * <=
     */
    CriterionOperator LESS_THAN_EQUAL = new DefaultCriterionOperator("LESS_THAN_EQUAL");

    /**
     * IN
     */
    CriterionOperator IN = new DefaultCriterionOperator("IN");

    /**
     * NOT IN
     */
    CriterionOperator NOT_IN = new DefaultCriterionOperator("NOT_IN");

    /**
     * LIKE
     */
    CriterionOperator LIKE = new DefaultCriterionOperator("LIKE");

    /**
     * NOT LIKE
     */
    CriterionOperator NOT_LIKE = new DefaultCriterionOperator("NOT_LIKE");

    /**
     * BETWEEN AND
     */
    CriterionOperator BETWEEN = new DefaultCriterionOperator("BETWEEN");

    /**
     * NOT BETWEEN AND
     */
    CriterionOperator NOT_BETWEEN = new DefaultCriterionOperator("NOT_BETWEEN");


    @NonNull
    String getName();

}
