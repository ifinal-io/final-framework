

package org.finalframework.annotation.query;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * @author likly
 * @version 1.0
 * @date 2020-07-20 11:13:36
 * @see Limit
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Offset {
    String[] value() default {
            "<if test=\"${value} != null\">",
            "   #{value},",
            "</if>"
    };
}
