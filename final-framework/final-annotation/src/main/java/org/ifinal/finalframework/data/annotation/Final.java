package org.ifinal.finalframework.data.annotation;

import org.springframework.data.annotation.Immutable;

import java.lang.annotation.*;

/**
 * Annotate the {@linkplain java.lang.reflect.Field property} is {@literal final} which would not be updated.
 *
 * @author likly
 * @version 1.0.0
 * @see Immutable
 * @see PrimaryKey
 * @see Creator
 * @see ReadOnly
 * @since 1.0.0
 */
@Immutable
@Documented
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Final {

}
