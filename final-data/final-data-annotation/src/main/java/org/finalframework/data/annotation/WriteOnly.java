package org.finalframework.data.annotation;

import java.lang.annotation.*;

/**
 * The property annotated by {@link WriteOnly} will not be selected.
 * 被标记的对象不会生成 {@literal select}
 *
 * @author likly
 * @version 1.0
 * @date 2018-10-15 15:14
 * @since 1.0
 */
@Column
@Documented
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface WriteOnly {

}
