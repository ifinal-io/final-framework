

package org.finalframework.annotation.data;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

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

    @AliasFor(annotation = Column.class, value = "name")
    String value() default "";

    @AliasFor(annotation = Column.class, value = "value")
    String name() default "";
}
