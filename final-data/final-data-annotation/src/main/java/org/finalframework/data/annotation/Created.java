package org.finalframework.data.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.core.annotation.AliasFor;
import org.springframework.data.annotation.CreatedDate;

/**
 * 创建时间
 *
 * @author likly
 * @version 1.0
 * @date 2018-10-15 15:14
 * @see Column
 * @see LastModified
 * @since 1.0
 */
@Column
@Default
@ReadOnly
@CreatedDate
@Documented
@Index(Integer.MAX_VALUE - 100)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Created {
    @AliasFor("name")
    String value() default "";
    @AliasFor("value")
    String name() default "";
}
