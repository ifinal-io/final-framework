/*
 * Copyright 2020-2022 the original author or authors.
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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.ifinalframework.core.lang.Final;

/**
 * Tenant.
 *
 * @author iimik
 * @version 1.3.1
 * @since 1.3.1
 */
@Final
@Column(value = "${final.data.tenant.column:tenant}",
        insert = {
                "<choose>",
                "     <when test=\"${test}\">",
                "         #{${value}#if($typeHandler)",
                "             #if($javaType), javaType=$!{javaType.canonicalName}#end",
                "             , typeHandler=$!{typeHandler.canonicalName}#end}",
                "      </when>",
                "     <when test=\"tenant != null\">",
                "         #{tenant}",
                "     </when>",
                "     <otherwise>null</otherwise>",
                "</choose>"
        })
@Target({ElementType.TYPE, ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Tenant {
}
