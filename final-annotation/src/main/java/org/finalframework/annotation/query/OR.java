

package org.finalframework.annotation.query;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * @author likly
 * @version 1.0
 * @date 2020-06-07 00:30:22
 * @since 1.0
 */
@Criteria(AndOr.OR)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface OR {
}
