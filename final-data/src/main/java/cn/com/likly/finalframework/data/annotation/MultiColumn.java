package cn.com.likly.finalframework.data.annotation;

import org.springframework.data.annotation.Persistent;

import java.lang.annotation.*;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-15 15:14
 * @since 1.0
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Persistent
public @interface MultiColumn {

    String name() default "";

    String[] properties();

    boolean unique() default false;

    boolean nonnull() default false;

    boolean insertable() default true;

    boolean updatable() default true;

}
