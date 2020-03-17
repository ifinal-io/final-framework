package org.finalframework.data.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.data.annotation.ReadOnlyProperty;

/**
 * Marked the element is {@literal readonly}, it will not be {@literal insert} and {@literal update}. Such as a {@link
 * LastModified} column when it has a default value of {@literal NULL ON UPDATE NOW()}.
 *
 * @author likly
 * @version 1.0
 * @date 2018-10-15 15:14
 * @see Created
 * @see Version
 * @since 1.0
 */
@Column
@Documented
@ReadOnlyProperty
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ReadOnly {

}
