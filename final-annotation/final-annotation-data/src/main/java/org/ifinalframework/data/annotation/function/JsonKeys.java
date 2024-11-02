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

package org.ifinalframework.data.annotation.function;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;

/**
 * JsonKeys.
 *
 * @author iimik
 * @version 1.0.0
 * @since 1.0.0
 */
@Documented
@Function(annotation = JsonKeys.class,value = "JSON_KEYS(${column},${path})")
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface JsonKeys {

    /**
     * json_keys
     * @return json_keys
     */
    @AliasFor("path")
    String[] value() default "";

    /**
     * json path
     * @return json path
     */
    @AliasFor("value")
    String path() default "";

}
