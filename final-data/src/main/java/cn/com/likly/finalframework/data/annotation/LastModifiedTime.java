package cn.com.likly.finalframework.data.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.data.annotation.LastModifiedDate;

import java.lang.annotation.*;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-15 15:14
 * @since 1.0
 */
@Index(Integer.MAX_VALUE - 100)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@LastModifiedDate
public @interface LastModifiedTime {
    @AliasFor("column")
    String value() default "";

    @AliasFor("value")
    String column() default "";
}
