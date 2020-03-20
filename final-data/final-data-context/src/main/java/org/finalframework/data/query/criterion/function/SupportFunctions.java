package org.finalframework.data.query.criterion.function;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.finalframework.data.query.criterion.function.expression.AvgFunctionOperationExpression;
import org.finalframework.data.query.criterion.function.expression.DateFunctionOperationExpression;
import org.finalframework.data.query.criterion.function.expression.JsonContainsFunctionOperationExpression;
import org.finalframework.data.query.criterion.function.expression.JsonExtractFunctionOperationExpression;
import org.finalframework.data.query.criterion.function.expression.JsonUnquoteFunctionOperationExpression;
import org.finalframework.data.query.criterion.function.expression.MaxFunctionOperationExpression;
import org.finalframework.data.query.criterion.function.expression.MinFunctionOperationExpression;
import org.finalframework.data.query.criterion.function.expression.NumberAndFunctionOperationExpression;
import org.finalframework.data.query.criterion.function.expression.NumberNotFunctionOperationExpression;
import org.finalframework.data.query.criterion.function.expression.NumberOrFunctionOperationExpression;
import org.finalframework.data.query.criterion.function.expression.NumberXorFunctionOperationExpression;
import org.finalframework.data.query.criterion.function.expression.SumFunctionOperationExpression;

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
public @interface SupportFunctions {

    FunctionOperator value();

    Class<?>[] types() default {Object.class};
}
