package org.finalframework.data.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.core.annotation.AliasFor;

/**
 * Marked the element is a {@literal version} record, it's a {@link ReadOnly} column, it's value insert by {@link
 * Default}, and modified when {@literal update} whit {@literal column = column + 1}.
 *
 * @author likly
 * @version 1.0
 * @date 2019-10-22 14:34:21
 * @since 1.0
 */
@Column
@Default
@ReadOnly
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@org.springframework.data.annotation.Version
public @interface Version {

  @AliasFor("name")
  String value() default "";

  @AliasFor("value")
  String name() default "";
}
