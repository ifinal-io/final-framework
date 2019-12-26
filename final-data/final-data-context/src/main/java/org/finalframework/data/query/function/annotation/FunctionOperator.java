package org.finalframework.data.query.function.annotation;

import org.finalframework.data.query.function.expression.*;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 函数运算符
 *
 * @author likly
 * @version 1.0
 * @date 2019-12-17 14:04:08
 * @see DateFunctionOperationExpression
 * @see MinFunctionOperationExpression
 * @see MaxFunctionOperationExpression
 * @see SumFunctionOperationExpression
 * @see AvgFunctionOperationExpression
 * @see NumberAndFunctionOperationExpression
 * @see NumberOrFunctionOperationExpression
 * @see NumberXorFunctionOperationExpression
 * @see NumberNotFunctionOperationExpression
 * @see JsonExtractFunctionOperationExpression
 * @see JsonUnquoteFunctionOperationExpression
 * @since 1.0
 */
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface FunctionOperator {

    /**
     * DATE(value)
     *
     * @see DateFunctionOperationExpression
     */
    String DATE = "DATE";
    /**
     * MIN(value)
     *
     * @see MinFunctionOperationExpression
     */
    String MIN = "MIN";
    /**
     * MAX(value)
     *
     * @see MaxFunctionOperationExpression
     */
    String MAX = "MAX";
    /**
     * SUM(value)
     *
     * @see SumFunctionOperationExpression
     */
    String SUM = "SUM";
    /**
     * AVG(value)
     *
     * @see AvgFunctionOperationExpression
     */
    String AVG = "AVG";
    /**
     * a & b
     *
     * @see NumberAndFunctionOperationExpression
     */
    String AND = "AND";
    /**
     * a | b
     *
     * @see NumberOrFunctionOperationExpression
     */
    String OR = "OR";
    /**
     * ~a
     *
     * @see NumberNotFunctionOperationExpression
     */
    String NOT = "NOT";
    /**
     * a ^ b
     *
     * @see NumberXorFunctionOperationExpression
     */
    String XOR = "XOR";
    /**
     * JSON_EXTRACT(json, path)
     *
     * @see JsonExtractFunctionOperationExpression
     */
    String JSON_EXTRACT = "JSON_EXTRACT";
    /**
     * JSON_UNQUOTE(value)
     *
     * @see JsonUnquoteFunctionOperationExpression
     */
    String JSON_UNQUOTE = "JSON_UNQUOTE";

    /**
     * CONCAT(prefix,value,suffix)
     *
     * @see ConcatFunctionOperationExpression
     */
    String CONCAT = "CONCAT";

    String value();

    Class<?>[] types() default {Object.class};
}
