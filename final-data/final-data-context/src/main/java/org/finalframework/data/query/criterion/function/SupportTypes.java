package org.finalframework.data.query.criterion.function;

import org.finalframework.data.query.criterion.function.expression.*;

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
 * @see JsonContainsFunctionOperationExpression
 * @see JsonUnquoteFunctionOperationExpression
 * @since 1.0
 */
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface SupportTypes {

    Class<?>[] types() default {Object.class};
}
