/*
 * Copyright 2020-2023 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ifinalframework.data.annotation.criterion;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.ifinalframework.data.query.CriterionAttributes;

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
 *     <li>use {@link StartsWith} for `column LIKE CONCAT('%',#{value})`</li>
 *     <li>use {@link NotStartsWith} for `column NOT LIKE CONCAT('%',#{value})`</li>
 *     <li>use {@link EndsWith} for `column LIKE CONCAT(#{value},'%')`</li>
 *     <li>use {@link NotEndsWith} for `column NOT LIKE CONCAT(#{value},'%')`</li>
 * </ul>
 *
 * @author iimik
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
 * @see StartsWith
 * @see NotStartsWith
 * @see EndsWith
 * @see NotEndsWith
 * @see In
 * @see NotIn
 * @see Between
 * @see NotBetween
 * @since 1.0.0
 */
@Target({ElementType.ANNOTATION_TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Criterion {

    String property() default "";

    String[] value();

    Class<?> javaType() default Object.class;

    Class<? extends Annotation> annotation() default Criterion.class;

}
