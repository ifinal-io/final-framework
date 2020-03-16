package org.finalframework.data.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.core.annotation.AliasFor;
import org.springframework.data.annotation.Persistent;

/**
 * Mark the element of {@link Field} or {@link Method} is persistent to {@literal json}. By Default,
 * the type of collection like {@link List},{@link Set} and {@link Map} will be persistent to
 * {@literal json}.
 *
 * @author likly
 * @version 1.0
 * @date 2018-10-15 15:14
 * @see Column
 * @since 1.0
 */
@Documented
@Persistent
@Column
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface JsonColumn {

  @AliasFor("name")
  String value() default "";

  @AliasFor("value")
  String name() default "";
}
