/*
 * Copyright 2020-2021 the original author or authors.
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

package org.ifinalframework.data.annotation;

import org.ifinalframework.core.lang.Transient;

import org.springframework.core.annotation.AliasFor;
import org.springframework.data.annotation.Persistent;

import java.lang.annotation.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Annotate a property of {@linkplain Field field} or {@linkplain Method method} mapping to a column of data table.
 *
 * @author iimik
 * @version 1.0.0
 * @see NameConverter
 * @see Table
 * @see Reference
 * @see Json
 * @see Virtual
 * @see Version
 * @see Created
 * @see Creator
 * @see LastModified
 * @see LastModifier
 * @see Transient
 * @since 1.0.0
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Persistent
public @interface Column {

    /**
     * return the column name of {@code property} mapped.
     *
     * @return column name
     * @see #name()
     */
    @AliasFor("name")
    String value() default "";

    /**
     * @return column name
     * @see #value()
     */
    @AliasFor("value")
    String name() default "";

    /**
     * return the column writer expression
     *
     * <pre class="code">
     *      #{value,javaType=,typeHandler}
     * </pre>
     *
     * @return the column writer
     */
    String[] insert() default {
            "#{${value}#if($typeHandler)",
            "#if($javaType), javaType=$!{javaType.canonicalName}#end",
            ", typeHandler=$!{typeHandler.canonicalName}#end}"
    };


    /**
     * select column
     *
     * @since 1.2.3
     */
    String select() default "${column}";

    /**
     * <pre class="code">
     * column = value
     * </pre>
     *
     * @since 1.2.3
     */
    String[] update() default {
            "<choose>",
            "   <when test=\"${selectiveTest}\">",
            "       ${column} = #{${value}#if($typeHandler)",
            "           #if($javaType), javaType=$!{javaType.canonicalName}#end",
            "           , typeHandler=$!{typeHandler.canonicalName}#end},",
            "   </when>",
            "   <when test=\"${test}\">",
            "       ${column} = #{${value}#if($typeHandler)",
            "           #if($javaType), javaType=$!{javaType.canonicalName}#end",
            "           , typeHandler=$!{typeHandler.canonicalName}#end},",
            "   </when>",
            "</choose>"
    };


    /**
     * {@code column = values(column)}
     *
     * @since 1.2.3
     */
    String onDuplicateKey() default "${column} = values(${column})";

    /**
     * return the property java type
     *
     * @return the property java type
     */
    Class<?> javaType() default Object.class;

}
