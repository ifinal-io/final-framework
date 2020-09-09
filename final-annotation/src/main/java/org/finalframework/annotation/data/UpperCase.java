

package org.finalframework.annotation.data;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * mark the {@link Class} or {@link java.lang.reflect.Field} use {@link String#toUpperCase()}.
 *
 * @author likly
 * @version 1.0
 * @date 2020-05-20 22:00:28
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD})
public @interface UpperCase {
}
