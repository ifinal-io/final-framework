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

package org.finalframework.data.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;
import org.springframework.data.annotation.Persistent;

/**
 * 标记一个 {@link java.lang.reflect.Field} 或者 {@link java.lang.reflect.Method} 为数据库和列。
 *
 * @author likly
 * @version 1.0
 * @date 2018-10-15 15:14
 * @see Reference
 * @see Json
 * @see Virtual
 * @see Created
 * @see Creator
 * @see LastModified
 * @see LastModifier
 * @see Transient
 * @since 1.0
 */
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Persistent
public @interface Column {

    @AliasFor("name")
    String value() default "";

    @AliasFor("value")
    String name() default "";

    String writer() default "#{${value}#if($javaType),javaType=$!{javaType.canonicalName}#end #if($typeHandler),typeHandler=$!{typeHandler.canonicalName}#end}";

    String reader() default "${column}";
}
