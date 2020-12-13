package org.ifinal.finalframework.annotation.query;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Generated the sql of {@literal column LIKE CONCAT('%',#{value},'%')}
 *
 * <pre class="code">
 *         &lt;if test="value != null and value != ''"&gt;
 *              column LIKE CONCAT('%',#{value},'%')
 *         &lt;if&gt;
 * </pre>
 *
 * @author likly
 * @version 1.0.0
 * @see NotContains
 * @see StartWith
 * @see NotStartWith
 * @see EndWith
 * @see NotEndWith
 * @see Like
 * @see NotLike
 * @since 1.0.0
 */
@Criterion(Contains.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Contains {

    String property() default "";

    String[] value() default {
        "<if test=\"${value} != null and ${value} != ''\">",
        "     ${andOr} ${column} LIKE CONCAT('%',#{${value}},'%') ",
        "</if>"
    };

    Class<?> javaType() default Object.class;

}
