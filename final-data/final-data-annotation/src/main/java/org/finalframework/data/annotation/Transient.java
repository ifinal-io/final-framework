package org.finalframework.data.annotation;

import java.lang.annotation.*;

/**
 * 被标记的{@link java.lang.reflect.Field} 或 {@link java.lang.reflect.Method}将不会映射框架解析。
 *
 * @author likly
 * @version 1.0
 * @date 2019-08-08 19:51:19
 * @since 1.0
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@org.springframework.data.annotation.Transient
public @interface Transient {
}
