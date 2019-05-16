package org.finalframework.data.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 视图，根据视图生成SQL。
 * 功能参考 {@link com.fasterxml.jackson.annotation.JsonView}
 *
 * @author likly
 * @version 1.0
 * @date 2019-03-09 14:20:10
 * @since 1.0
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ColumnView {
    Class<?>[] value();
}
