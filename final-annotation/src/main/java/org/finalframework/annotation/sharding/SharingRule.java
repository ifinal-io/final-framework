package org.finalframework.annotation.sharding;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author likly
 * @version 1.0
 * @date 2020/10/26 11:18:57
 * @since 1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface SharingRule {
    /**
     * return the logic table name
     *
     * @return the logic table name
     */
    String logicTable() default "";
}
