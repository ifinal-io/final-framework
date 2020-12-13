package org.ifinal.finalframework.annotation.data;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.data.annotation.Immutable;

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
