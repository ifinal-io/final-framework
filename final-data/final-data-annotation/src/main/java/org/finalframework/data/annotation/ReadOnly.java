package org.finalframework.data.annotation;

import org.springframework.data.annotation.ReadOnlyProperty;

import java.lang.annotation.*;

/**
 * The property annotated by {@link ReadOnly} will not be updated.
 * 被标记的对象不会生成 {@literal insert} 和 {@literal update}
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
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ReadOnly {

}
