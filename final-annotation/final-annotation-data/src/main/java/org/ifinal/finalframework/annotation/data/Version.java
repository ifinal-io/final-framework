package org.ifinal.finalframework.annotation.data;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.ifinal.finalframework.annotation.core.lang.Default;
import org.springframework.core.annotation.AliasFor;

/**
 * Marked the element is a {@literal version} record, it's a {@link ReadOnly} column, it's value insert by {@link
 * Default}, such as {@literal 1}, and modified when {@literal update} whit {@literal column = column + 1}.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Column
@Default
@ReadOnly
@Documented
@Order(Integer.MAX_VALUE - 200)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@org.springframework.data.annotation.Version
public @interface Version {

    @AliasFor(annotation = Column.class, value = "name")
    String value() default "";

    @AliasFor(annotation = Column.class, value = "value")
    String name() default "";

}
