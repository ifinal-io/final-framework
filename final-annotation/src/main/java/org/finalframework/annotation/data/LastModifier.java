package org.finalframework.annotation.data;

import org.finalframework.annotation.IUser;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * Annotate the {@linkplain java.lang.reflect.Field property} represent a {@linkplain IUser last modifier}.
 *
 * @author likly
 * @version 1.0
 * @date 2018-10-15 15:14
 * @see Creator
 * @see IUser
 * @since 1.0
 */
@Column
@Documented
@Order(Ordered.LAST_MODIFIER)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LastModifier {

    @AliasFor(annotation = Column.class, value = "name")
    String value() default "";

    @AliasFor(annotation = Column.class, value = "value")
    String name() default "";
}
