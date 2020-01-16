package org.finalframework.coding.datasource.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author likly
 * @version 1.0
 * @date 2020-01-14 10:10:20
 * @since 1.0
 */
@Target({ElementType.PACKAGE, ElementType.TYPE})
@Retention(RetentionPolicy.SOURCE)
public @interface ShardingDataSource {
    String name() default "";

    String prefix();

    String[] basePackages();

    TableRule[] tableRules();

}
