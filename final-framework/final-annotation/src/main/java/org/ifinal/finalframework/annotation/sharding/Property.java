package org.ifinal.finalframework.annotation.sharding;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Property {

    String INLINE_ALGORITHM_EXPRESSION = "algorithm-expression";
    String INLINE_SHARING_COLUMNS = "sharding-columns";
    String INLINE_ALLOW_RANGE_QUERY = "allow-range-query-with-inline-sharding";

    String INTERVAL_DATETIME_PATTERN = "datetime-pattern";
    String INTERVAL_DATETIME_LOWER = "datetime-lower";
    String INTERVAL_DATETIME_UPPER = "datetime-upper";
    String INTERVAL_SHARDING_SUFFIX_PATTERN = "sharding-suffix-pattern";
    String INTERVAL_DATETIME_INTERVAL_AMOUNT = "datetime-interval-amount";
    String INTERVAL_DATETIME_INTERVAL_UNIT = "datetime-interval-unit";


    String CLASS_BASED_STRATEGY = "strategy";
    String CLASS_BASED_ALGORITHM_CLASS_NAME = "algorithmClassName";


    String value() default "";
}
