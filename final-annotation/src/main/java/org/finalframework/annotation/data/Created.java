

package org.finalframework.annotation.data;

import org.springframework.core.annotation.AliasFor;
import org.springframework.data.annotation.CreatedDate;

import java.lang.annotation.*;

/**
 * Mark the {@link java.lang.reflect.Field} is a created column which is have a {@link Default} value and it is {@link Final} and {@link ReadOnly}.
 *
 * @author likly
 * @version 1.0
 * @date 2018-10-15 15:14
 * @see Column
 * @see Default NOW()
 * @see Final can not update
 * @see LastModified
 * @since 1.0
 */
@Column
@Default
@Final
@ReadOnly
@CreatedDate
@Documented
@Order(Integer.MAX_VALUE - 120)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Created {
    @AliasFor(annotation = Column.class)
    String value() default "";

    @AliasFor(annotation = Column.class)
    String name() default "";
}
