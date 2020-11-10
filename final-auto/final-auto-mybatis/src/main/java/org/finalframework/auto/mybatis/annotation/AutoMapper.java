package org.finalframework.auto.mybatis.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * @author likly
 * @version 1.0
 * @date 2020-09-01 11:03:28
 * @since 1.0
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.PACKAGE)
public @interface AutoMapper {
    String[] value();

    String entity() default "entity";

    String mapper() default "dao.mapper";
}
