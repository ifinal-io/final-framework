

package org.finalframework.annotation.data;

import org.springframework.data.annotation.Immutable;

import java.lang.annotation.*;

/**
 * Mark the {@linkplain java.lang.reflect.Field property} annotated by {@link Final} is {@literal final} which can not be update.
 *
 * @author likly
 * @version 1.0
 * @date 2018-10-15 15:14
 * @see Immutable
 * @see PrimaryKey
 * @see Creator
 * @see ReadOnly
 * @since 1.0
 */
@Immutable
@Documented
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Final {

}
