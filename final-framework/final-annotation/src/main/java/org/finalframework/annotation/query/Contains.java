package org.finalframework.annotation.query;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Generated the sql of {@literal column LIKE CONCAT('%',#{value},'%')}
 *
 * <pre>
 *     <code>
 *         <if test="value != null and value != ''">
 *              column LIKE CONCAT('%',#{value},'%')
 *         </if>
 *     </code>
 * </pre>
 *
 * @author likly
 * @version 1.0
 * @date 2019-02-11 11:29:16
 * @see NotContains
 * @see StartWith
 * @see NotStartWith
 * @see EndWith
 * @see NotEndWith
 * @see Like
 * @see NotLike
 * @since 1.0
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
