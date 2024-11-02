/*
 * Copyright 2020-2021 the original author or authors.
 *
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
 *
 */

package org.ifinalframework.data.annotation;

import org.springframework.data.annotation.ReadOnlyProperty;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotate the element of {@link java.lang.reflect.Field} or {@link java.lang.reflect.Method} is a {@literal readonly}
 * property, it will not be {@literal insert} and {@literal update}. Such as a {@link LastModified} column when it has a
 * default value of {@literal NULL ON UPDATE NOW()}.
 *
 * @author iimik
 * @version 1.0.0
 * @see Created
 * @see Version
 * @since 1.0.0
 */
@Documented
@ReadOnlyProperty
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ReadOnly {

}
