package org.finalframework.annotation.data;

import org.springframework.data.annotation.ReadOnlyProperty;

import java.lang.annotation.*;

/**
 * Annotate the element of {@link java.lang.reflect.Field} or {@link java.lang.reflect.Method} is a {@literal readonly} property,
 * it will not be {@literal insert} and {@literal update}. Such as a {@link LastModified} column when it has a default value of {@literal NULL ON UPDATE NOW()}.
 *
 * @author likly
 * @version 1.0
 * @date 2018-10-15 15:14
 * @see Created
 * @see Version
 * @since 1.0
 */
@Documented
@ReadOnlyProperty
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ReadOnly {

}
