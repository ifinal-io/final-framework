package org.ifinal.finalframework.annotation.data;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

/**
 * Annotate the {@link Field property} is a sql keyword which should be warped like `{property}`.
 *
 * <h3>Example:</h3>
 * <ul>
 *     <li>`order`</li>
 * </ul>
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Keyword {

}
