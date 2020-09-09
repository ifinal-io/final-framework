

package org.finalframework.annotation.data;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Mark the property is a {@link Sharding} column which will not be appear the {@code ON DUPLICATE KEY UPDATE }.
 *
 * @author likly
 * @version 1.0
 * @date 2020-03-19 09:11:44
 * @since 1.0
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Sharding {

}
