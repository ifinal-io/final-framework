package org.ifinal.finalframework.annotation.data;

import org.springframework.core.annotation.AliasFor;
import org.springframework.data.annotation.LastModifiedDate;

import java.lang.annotation.*;

/**
 * 最后更新时间
 *
 * @author likly
 * @version 1.0.0
 * @see Created
 * @see LastModifier
 * @since 1.0.0
 */
@ReadOnly
@Column
@Documented
@LastModifiedDate
@Order(Ordered.LAST_MODIFIED)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface LastModified {

    @AliasFor(annotation = Column.class, value = "name")
    String value() default "";

    @AliasFor(annotation = Column.class, value = "value")
    String name() default "";
}
