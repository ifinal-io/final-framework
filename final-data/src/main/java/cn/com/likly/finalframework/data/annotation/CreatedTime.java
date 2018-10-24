package cn.com.likly.finalframework.data.annotation;

import org.springframework.data.annotation.CreatedDate;

import java.lang.annotation.*;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-15 15:14
 * @since 1.0
 */
@Index(Integer.MAX_VALUE - 100)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@CreatedDate
public @interface CreatedTime {
    String table() default "";

    String name() default "";

    boolean unique() default false;

    boolean nullable() default false;

    boolean insertable() default false;

    boolean updatable() default false;
}
