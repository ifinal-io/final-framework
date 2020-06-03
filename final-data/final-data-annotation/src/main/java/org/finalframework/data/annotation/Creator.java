package org.finalframework.data.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.core.annotation.AliasFor;
import org.springframework.data.annotation.CreatedBy;

/**
 * 创建人
 *
 * @author likly
 * @version 1.0
 * @date 2018-10-15 15:14
 * @see Column
 * @see Created
 * @see LastModifier
 * @see LastModified
 * @see IUser
 * @since 1.0
 */
@Final
@Column
@Documented
@CreatedBy
@Order(Integer.MAX_VALUE - 100)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Creator {

    @AliasFor(annotation = Column.class, value = "name")
    String value() default "";

    @AliasFor(annotation = Column.class, value = "value")
    String name() default "";
}
