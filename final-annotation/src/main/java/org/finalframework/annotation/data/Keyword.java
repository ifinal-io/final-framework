package org.finalframework.annotation.data;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation the {@link java.lang.reflect.Field property} is a sql keyword which should be warped like `{property}`.
 *
 * @author likly
 * @version 1.0
 * @date 2020-03-26 13:35:53
 * @since 1.0
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Keyword {

}
