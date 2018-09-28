package cn.com.likly.finalframework.mybatis.annotation;

import cn.com.likly.finalframework.mybatis.annotation.enums.JdbcType;

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
public @interface ID {
    int index() default Integer.MIN_VALUE;

    String column() default "";

    JdbcType jdbcType() default JdbcType.DEFAULT;

    boolean notnull() default true;

    boolean unique() default true;

    boolean insert() default false;

    boolean update() default false;

    boolean select() default true;

    String description() default "流水号";

}
