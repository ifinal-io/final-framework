package org.finalframework.data.annotation;

import java.lang.annotation.*;

/**
 * 被标记的对象不会生成 {@literal insert}
 *
 * @author likly
 * @version 1.0
 * @date 2020-03-15 22:59:10
 * @see PrimaryKey
 * @see Version
 * @see Created
 * @see LastModified
 * @since 1.0
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Default {
}
