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

import org.ifinalframework.core.lang.Default;
import org.springframework.core.annotation.AliasFor;
import org.springframework.core.annotation.Order;

import java.lang.annotation.*;

/**
 * Marked the element is a {@literal version} record, it's a {@link ReadOnly} column, it's value insert by {@link
 * Default}, such as {@literal 1}, and modified when {@literal update} whit {@literal column = column + 1}.
 *
 * @author iimik
 * @version 1.0.0
 * @see AbsEntity
 * @since 1.0.0
 */
@Column(
        update = "${column} = ${column} + 1",
        onDuplicateKey = "${column} = values(${column}) + 1"
)
@Default
@ReadOnly
@Documented
@Order(Integer.MAX_VALUE - 200)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@org.springframework.data.annotation.Version
public @interface Version {

    /**
     * Alias for {@link Column#value()}.
     */
    @AliasFor(annotation = Column.class)
    String value() default "";

    /**
     * Alias for {@link Column#name()}.
     */
    @AliasFor(annotation = Column.class)
    String name() default "";

}
