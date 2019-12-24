package org.finalframework.data.query.criterion.function.operation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author likly
 * @version 1.0
 * @date 2019-12-17 14:04:08
 * @see DateFunctionCriterionOperation
 * @see MinFunctionCriterionOperation
 * @see MaxFunctionCriterionOperation
 * @see SumFunctionCriterionOperation
 * @see AvgFunctionCriterionOperation
 * @see NumberAndFunctionCriterionOperation
 * @see NumberOrFunctionCriterionOperation
 * @see NumberXorFunctionCriterionOperation
 * @see NumberNotFunctionCriterionOperation
 * @see JsonExtractFunctionCriterionOperation
 * @since 1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface FunctionOperation {

    /**
     * DATE(value)
     *
     * @see DateFunctionCriterionOperation
     */
    String DATE = "DATE";
    /**
     * MIN(value)
     *
     * @see MinFunctionCriterionOperation
     */
    String MIN = "MIN";
    /**
     * MAX(value)
     *
     * @see MaxFunctionCriterionOperation
     */
    String MAX = "MAX";
    /**
     * SUM(value)
     *
     * @see SumFunctionCriterionOperation
     */
    String SUM = "SUM";
    /**
     * AVG(value)
     *
     * @see AvgFunctionCriterionOperation
     */
    String AVG = "AVG";
    /**
     * a & b
     *
     * @see NumberAndFunctionCriterionOperation
     */
    String AND = "AND";
    /**
     * a | b
     *
     * @see NumberOrFunctionCriterionOperation
     */
    String OR = "OR";
    /**
     * ~a
     *
     * @see NumberNotFunctionCriterionOperation
     */
    String NOT = "NOT";
    /**
     * a ^ b
     *
     * @see NumberXorFunctionCriterionOperation
     */
    String XOR = "XOR";
    /**
     * JSON_EXTRACT(json, path)
     *
     * @see JsonExtractFunctionCriterionOperation
     */
    String JSON_EXTRACT = "JSON_EXTRACT";
    /**
     * JSON_UNQUOTE(value)
     *
     * @see JsonExtractFunctionCriterionOperation
     */
    String JSON_UNQUOTE = "JSON_UNQUOTE";

    String value();

    Class<?>[] types() default {Object.class};
}
