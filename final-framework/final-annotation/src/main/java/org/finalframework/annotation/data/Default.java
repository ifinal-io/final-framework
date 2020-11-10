package org.finalframework.annotation.data;

import java.lang.annotation.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Annotate the element of {@linkplain Field field} or {@linkplain Method method} is not need to {@literal insert} which has a default value.
 * Such as a {@link Created} column when it default {@literal NOW()}.
 *
 * @author likly
 * @version 1.0
 * @date 2020-03-15 22:59:10
 * @see PrimaryKey
 * @see Version
 * @see Created
 * @see LastModified
 * @since 1.0
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Default {

}
