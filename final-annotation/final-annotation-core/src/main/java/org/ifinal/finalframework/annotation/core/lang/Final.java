package org.ifinal.finalframework.annotation.core.lang;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotate the {@linkplain java.lang.reflect.Field property} is {@literal final} which would not be updated.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Documented
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Final {

}
