package org.finalframework.data.annotation;

import org.finalframework.data.annotation.enums.PrimaryKeyType;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 标记{@link java.lang.reflect.Field}映射为主键。
 *
 * @author likly
 * @version 1.0
 * @date 2018-10-15 15:14
 * @see org.springframework.data.annotation.Id
 * @see PrimaryKeyType
 * @since 1.0
 */
@Column
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
