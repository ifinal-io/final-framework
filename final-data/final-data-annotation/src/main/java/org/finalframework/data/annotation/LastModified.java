package org.finalframework.data.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.core.annotation.AliasFor;
import org.springframework.data.annotation.LastModifiedDate;

/**
 * 最后更新时间
 *
 * @author likly
 * @version 1.0
 * @date 2018-10-15 15:14
 * @see Created
 * @since 1.0
 */
@ReadOnly
@Column
@Documented
@LastModifiedDate
@Index(Integer.MAX_VALUE - 99)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LastModified {

    @AliasFor(annotation = Column.class, value = "name")
    String value() default "";

    @AliasFor(annotation = Column.class, value = "value")
    String name() default "";
}
