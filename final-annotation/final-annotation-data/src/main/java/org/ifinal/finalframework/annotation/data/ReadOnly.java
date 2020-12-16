package org.ifinal.finalframework.annotation.data;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.data.annotation.ReadOnlyProperty;

/**
 * Annotate the element of {@link java.lang.reflect.Field} or {@link java.lang.reflect.Method} is a {@literal readonly}
 * property, it will not be {@literal insert} and {@literal update}. Such as a {@link LastModified} column when it has a
 * default value of {@literal NULL ON UPDATE NOW()}.
 *
 * @author likly
 * @version 1.0.0
 * @see Created
 * @see Version
 * @since 1.0.0
 */
@Documented
@ReadOnlyProperty
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ReadOnly {

}
