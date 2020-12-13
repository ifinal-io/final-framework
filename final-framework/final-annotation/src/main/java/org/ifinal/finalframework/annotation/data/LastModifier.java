package org.ifinal.finalframework.annotation.data;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.ifinal.finalframework.annotation.core.IUser;
import org.springframework.core.annotation.AliasFor;

/**
 * Annotate the {@linkplain java.lang.reflect.Field property} represent a {@linkplain IUser last modifier}.
 *
 * @author likly
 * @version 1.0.0
 * @see Creator
 * @see IUser
 * @since 1.0.0
 */
@Column
@Documented
@Order(Integer.MAX_VALUE - 110)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LastModifier {

    @AliasFor(annotation = Column.class, value = "name")
    String value() default "";

    @AliasFor(annotation = Column.class, value = "value")
    String name() default "";

}
