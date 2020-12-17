package org.ifinal.finalframework.annotation.data;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.ifinal.finalframework.annotation.core.IUser;
import org.ifinal.finalframework.annotation.core.lang.Final;
import org.springframework.core.annotation.AliasFor;
import org.springframework.data.annotation.CreatedBy;

/**
 * Annotate the {@linkplain java.lang.reflect.Field property} represent the {@linkplain IUser creator} which is
 * {@linkplain Final}.
 *
 * @author likly
 * @version 1.0.0
 * @see Column
 * @see Created
 * @see LastModifier
 * @see LastModified
 * @see IUser
 * @since 1.0.0
 */
@Final
@Column
@Documented
@CreatedBy
@Order(Integer.MAX_VALUE - 130)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Creator {

    @AliasFor(annotation = Column.class)
    String value() default "";

    @AliasFor(annotation = Column.class)
    String name() default "";

}
