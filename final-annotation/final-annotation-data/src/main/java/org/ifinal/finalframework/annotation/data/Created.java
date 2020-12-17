package org.ifinal.finalframework.annotation.data;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import org.ifinal.finalframework.annotation.core.lang.Default;
import org.ifinal.finalframework.annotation.core.lang.Final;
import org.springframework.core.annotation.AliasFor;
import org.springframework.data.annotation.CreatedDate;

/**
 * Annotate the {@link Field property} is a {@code created} column which is have a {@link Default} value of {@code
 * NOW()} and it is {@link Final} and {@link ReadOnly}.
 *
 * @author likly
 * @version 1.0.0
 * @see Column
 * @see Default NOW()
 * @see Final can not update
 * @since 1.0.0
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
