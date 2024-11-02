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

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.ifinalframework.data.query.condition.JsonCondition;

/**
 * Generate sql fragment like {@code JSON_CONTAINS(${column},'${value}'[,'${path}']})}
 *
 * @author iimik
 * @version 1.4.0
 * @see NotJsonContains
 * @see JsonCondition#jsonContains(Object)
 * @since 1.4.0
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Criterion(annotation = JsonArrayContains.class, value = {
        "<if test=\"${value} != null\">",
        "   <![CDATA[",
        "       ${andOr} JSON_CONTAINS( ${column}, JSON_ARRAY(#{${value}",
        "           #if($javaType), javaType=$!{javaType.canonicalName}#end",
        "           #if($typeHandler), typeHandler=$!{typeHandler.canonicalName}#end})",
        "       #if($path), '${path}'#end)",
        "   ]]>",
        "</if>"
})
public @interface JsonArrayContains {

    /**
     * property name
     *
     * @return property nam
     */
    String property() default "";


    /**
     * path
     *
     * @return path
     */
    String path() default "";

    /**
     * java type
     *
     * @return javaType
     */
    Class<?> javaType() default Object.class;

}
