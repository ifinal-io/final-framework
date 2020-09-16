package org.finalframework.annotation.data;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Custom the {@link org.apache.ibatis.type.TypeHandler} for mybatis.
 *
 * @author likly
 * @version 1.0
 * @date 2019-02-26 09:31:58
 * @since 1.0
 */
@Target({ElementType.ANNOTATION_TYPE, ElementType.TYPE, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface TypeHandler {
    Class<? extends org.apache.ibatis.type.TypeHandler<?>> value();
}
