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

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.ifinalframework.core.IEntity;

/**
 * Annotate the {@linkplain IEntity entity} mapping a special table in datasource by {@link #value()}.
 *
 * <p>Use example:</p>
 * <pre class="code">
 *     &#64;Table("t_person")
 *     public class Person extends AbsEntity{
 *
 *     }
 * </pre>
 *
 * @author iimik
 * @version 1.0.0
 * @since 1.0.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Table {

    /**
     * return the special table name when the {@link Class#getSimpleName()} of {@linkplain IEntity entity} can not mapped the table.
     *
     * @return the special table name.
     */
    String[] value() default {};


}
