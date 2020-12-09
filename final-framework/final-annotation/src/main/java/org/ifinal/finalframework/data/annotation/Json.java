package org.ifinal.finalframework.data.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Annotate the element of {@link Field} or {@link Method} is persistent to {@literal json}. The type of
 * collection like {@link List},{@link Set} and {@link Map} will be persistent to {@literal json} by default.
 *
 * @author likly
 * @version 1.0.0
 * @see Column
 * @since 1.0.0
 */
@Column
@Documented
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Json {

    @AliasFor(annotation = Column.class)
    String value() default "";

    @AliasFor(annotation = Column.class)
    String name() default "";
}
