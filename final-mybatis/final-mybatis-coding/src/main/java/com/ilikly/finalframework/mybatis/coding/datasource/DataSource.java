package com.ilikly.finalframework.mybatis.coding.datasource;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-08 10:11
 * @since 1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface DataSource {
    String name() default "";

    String prefix();

    String[] basePackages();

    String[] mapperLocations() default {};
}
