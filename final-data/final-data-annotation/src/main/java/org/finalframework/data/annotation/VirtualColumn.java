package org.finalframework.data.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.core.annotation.AliasFor;
import org.springframework.data.annotation.Persistent;

/**
 * Marked the element is a {@literal virtual}.
 *
 * @author likly
 * @version 1.0
 * @date 2020-03-16 22:59
 * @see Column
 * @see ReadOnly
 * @since 1.0
 */
@Documented
@Persistent
@Column
@ReadOnly
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface VirtualColumn {

  @AliasFor("name")
  String value() default "";

  @AliasFor("value")
  String name() default "";
}
