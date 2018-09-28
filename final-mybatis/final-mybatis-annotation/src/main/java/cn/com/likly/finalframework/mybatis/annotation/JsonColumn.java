package cn.com.likly.finalframework.mybatis.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-26 21:53
 * @since 1.0
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface JsonColumn {
    int index() default 0;

    String column() default "";

    boolean notnull() default false;

    boolean unique() default false;

    boolean insert() default true;

    boolean update() default true;

    boolean select() default true;

    String description() default "";

}
