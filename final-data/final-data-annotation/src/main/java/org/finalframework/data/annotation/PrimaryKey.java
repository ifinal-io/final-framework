package org.finalframework.data.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.finalframework.data.annotation.enums.PrimaryKeyType;
import org.springframework.core.annotation.AliasFor;

/**
 * Marked the element is a primary key.
 *
 * @author likly
 * @version 1.0
 * @date 2018-10-15 15:14
 * @see org.springframework.data.annotation.Id
 * @see PrimaryKeyType
 * @see IEntity
 * @since 1.0
 */
@Column
@Default
@Documented
@Index(Integer.MIN_VALUE)
@org.springframework.data.annotation.Id
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PrimaryKey {

  @AliasFor(annotation = Column.class, value = "value")
  String value() default "";

  @AliasFor(annotation = Column.class, value = "name")
  String name() default "";

  PrimaryKeyType type() default PrimaryKeyType.AUTO_INC;
}
