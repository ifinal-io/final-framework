package org.finalframework.spring.annotation.factory;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author likly
 * @version 1.0
 * @date 2019-11-07 13:29:48
 * @since 1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
@SpringFactory(value = SpringComponent.class,expand = true)
public @interface SpringComponent {
}
