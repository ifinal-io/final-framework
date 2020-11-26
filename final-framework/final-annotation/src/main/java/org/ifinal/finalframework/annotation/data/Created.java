package org.ifinal.finalframework.annotation.data;

import org.springframework.core.annotation.AliasFor;
import org.springframework.data.annotation.CreatedDate;

import java.lang.annotation.*;
import java.lang.reflect.Field;

/**
 * Annotate the {@link Field property} is a {@code created} column which is have a {@link Default} value of {@code NOW()} and it is {@link Final} and {@link ReadOnly}.
 *
 * @author likly
 * @version 1.0.0
 * @see Column
 * @see Default NOW()
 * @see Final can not update
 * @see LastModified
 * @since 1.0.0
 */
@Column
@Default
@Final
@ReadOnly
@CreatedDate
@Documented
@Order(Ordered.CREATED)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Created {
    @AliasFor(annotation = Column.class)
    String value() default "";

    @AliasFor(annotation = Column.class)
    String name() default "";
}
