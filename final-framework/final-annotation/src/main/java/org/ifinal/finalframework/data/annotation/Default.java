package org.ifinal.finalframework.data.annotation;

import java.lang.annotation.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Annotate the element of {@linkplain Field field} or {@linkplain Method method} is not need to {@literal insert} which has a default value.
 * Such as a {@link Created} column when it default {@literal NOW()}.
 *
 * @author likly
 * @version 1.0.0
 * @see PrimaryKey
 * @see Version
 * @see Created
 * @see LastModified
 * @since 1.0.0
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Default {

}
