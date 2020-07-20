/*
 * Copyright (c) 2018-2020.  the original author or authors.
 *  <p>
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  <p>
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  <p>
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package org.finalframework.data.annotation.query;

import org.springframework.core.annotation.AliasFor;

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
 * @see NOT_CONTAINS
 * @see START_WITH
 * @see NOT_START_WITH
 * @see END_WITH
 * @see NOT_END_WITH
 * @see LIKE
 * @see NOT_LIKE
 * @since 1.0
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Criterion
public @interface CONTAINS {
    @AliasFor(annotation = Criterion.class, attribute = "property")
    String property() default "";

    @AliasFor(annotation = Criterion.class, attribute = "value")
    String[] value() default {
            "<if test=\"${value} != null and ${value} != ''\">",
            "    ${column} LIKE CONCAT('%',#{value},'%') ",
            "</if>"
    };

    @AliasFor(annotation = Criterion.class, attribute = "javaType")
    Class<?> javaType() default Object.class;
}
