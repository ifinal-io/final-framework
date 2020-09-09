

package org.finalframework.annotation.query;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The meta annotation for criterion.
 *
 * @author likly
 * @version 1.0
 * @date 2019-02-20 09:25:43
 * @see Metadata
 * @see CriterionHandler
 * @see Equal
 * @see NotEqual
 * @see GreatThan
 * @see GreatThanEqual
 * @see LessThan
 * @see LessThanEqual
 * @see Like
 * @see NotLike
 * @see CONTAINS
 * @see NotContains
 * @see StartWith
 * @see NotStartWith
 * @see EndWith
 * @see NotEndWith
 * @see In
 * @see NotIn
 * @see Between
 * @see NotBetween
 * @since 1.0
 */
@Target({ElementType.ANNOTATION_TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Criterion {

    String property() default "";

    String[] value() default {};

    Class<?> javaType() default Object.class;

    Class<? extends CriterionHandler> handler() default CriterionHandler.class;

    Attribute[] attributes() default {};

}
