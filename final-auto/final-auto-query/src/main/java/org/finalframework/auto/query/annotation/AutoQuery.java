package org.finalframework.auto.query.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * @author likly
 * @version 1.0
 * @date 2020-09-01 13:16:01
 * @since 1.0
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.PACKAGE)
public @interface AutoQuery {

    String[] value();

    String entity() default "entity";

    String query() default "dao.query";
}
