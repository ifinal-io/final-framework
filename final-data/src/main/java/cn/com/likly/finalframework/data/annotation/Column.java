package cn.com.likly.finalframework.data.annotation;

import cn.com.likly.finalframework.data.annotation.enums.PersistentType;
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
public @interface Column {

    String table() default "";

    String name() default "";

    PersistentType persistentType() default PersistentType.AUTO;

    boolean unique() default false;

    boolean nonnull() default false;

    boolean insertable() default true;

    boolean updatable() default true;
}
