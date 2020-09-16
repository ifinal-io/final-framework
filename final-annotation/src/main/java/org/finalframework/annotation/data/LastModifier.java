package org.finalframework.annotation.data;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 最后更新时间
 *
 * @author likly
 * @version 1.0
 * @date 2018-10-15 15:14
 * @since 1.0
 */
@Column
@Documented
@Order(Integer.MAX_VALUE - 110)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LastModifier {

    @AliasFor(annotation = Column.class, value = "name")
    String value() default "";

    @AliasFor(annotation = Column.class, value = "value")
    String name() default "";
}
