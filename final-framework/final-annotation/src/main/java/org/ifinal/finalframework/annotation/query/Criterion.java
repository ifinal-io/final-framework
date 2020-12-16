package org.ifinal.finalframework.annotation.query;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The meta annotation to mark the {@link java.lang.annotation.Annotation} is a criterion annotation. The {@link
 * java.lang.annotation.Annotation} of criterion must have
 *
 * <ul>
 *     <li>a {@link String} attribute named `property`</li>
 *     <li>a {@link String} array attribute named `value`</li>
 *     <li>a {@link Class} attribute named `javaType`</li>
 * </ul>
 *
 *
 * <ul>
 *     <li>use {@link IsNull} for `column IS NULL`</li>
 *     <li>use {@link IsNotNull} for `column IS NOT NULL`</li>
 *     <li>use {@link Equal} for `column = #{value}`</li>
 *     <li>use {@link NotEqual} for `column != #{value}`</li>
 *     <li>use {@link GreatThan} for `column &gt; #{value}`</li>
 *     <li>use {@link GreatThanEqual} for `column &gt;= #{value}`</li>
 *     <li>use {@link LessThan} for `column &lt; #{value}`</li>
 *     <li>use {@link LessThanEqual} for `column &lt;= #{value}`</li>
 *     <li>use {@link Between} for `column BETWEEN #{min} AND #{max}`</li>
 *     <li>use {@link NotBetween} for `column NOT BETWEEN #{min} AND #{max}`</li>
 *     <li>use {@link In} for `column IN (#{value})`</li>
 *     <li>use {@link NotIn} for `column NOT IN (#{value})`</li>
 *     <li>use {@link Like} for `column LIKE #{value}`</li>
 *     <li>use {@link NotLike} for `column NOT LIKE #{value}`</li>
 *     <li>use {@link Contains} for `column LIKE CONCAT('%',#{value},'%')`</li>
 *     <li>use {@link NotContains} for `column NOT LIKE CONCAT('%',#{value},'%')`</li>
 *     <li>use {@link StartWith} for `column LIKE CONCAT('%',#{value})`</li>
 *     <li>use {@link NotStartWith} for `column NOT LIKE CONCAT('%',#{value})`</li>
 *     <li>use {@link EndWith} for `column LIKE CONCAT(#{value},'%')`</li>
 *     <li>use {@link NotEndWith} for `column NOT LIKE CONCAT(#{value},'%')`</li>
 * </ul>
 *
 * @author likly
 * @version 1.0.0
 * @see CriterionAttributes
 * @see CriterionSqlProvider
 * @see IsNull
 * @see IsNotNull
 * @see Equal
 * @see NotEqual
 * @see GreatThan
 * @see GreatThanEqual
 * @see LessThan
 * @see LessThanEqual
 * @see Like
 * @see NotLike
 * @see Contains
 * @see NotContains
 * @see StartWith
 * @see NotStartWith
 * @see EndWith
 * @see NotEndWith
 * @see In
 * @see NotIn
 * @see Between
 * @see NotBetween
 * @since 1.0.0
 */
@Target({ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Criterion {

    Class<? extends Annotation> value();

}
