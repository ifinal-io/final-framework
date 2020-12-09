package org.ifinal.finalframework.data.annotation;

import java.lang.annotation.*;
import java.lang.reflect.Field;


/**
 * Annotated the name of {@link Class table} or {@link Field column} should be convert to upper case use {@link String#toUpperCase()}.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD})
public @interface UpperCase {
}
