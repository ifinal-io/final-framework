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

package org.finalframework.annotation.data;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * @author likly
 * @version 1.0
 * @date 2020-07-17 13:09:04
 * @since 1.0
 */
@SuppressWarnings("all")
@Column(
        writer = "ST_GeomFromText(#{${value}#if($javaType),javaType=$!{javaType.canonicalName}#end #if($typeHandler),typeHandler=$!{typeHandler.canonicalName}#end}",
        reader = "ST_ASTEXT(${column}) as ${column}"
)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface Geometry {

    @AliasFor(annotation = Column.class)
    String value() default "";

    @AliasFor(annotation = Column.class)
    String name() default "";

}
