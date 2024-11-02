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

package org.ifinalframework.data.query;

import java.io.Serializable;

import org.ifinalframework.data.annotation.criterion.Between;
import org.ifinalframework.data.annotation.criterion.NotBetween;
import org.ifinalframework.data.query.condition.BetweenCondition;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The value holder which have one {@code min} value and {@code max} value for {@code between} value.
 *
 * <pre class="code">
 * BETWEEN {min} AND {max}
 * </pre>
 *
 * @author iimik
 * @version 1.0.0
 * @see Between
 * @see NotBetween
 * @see BetweenCondition
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BetweenValue<T> implements Serializable {

    private static final long serialVersionUID = 6194662646358531082L;

    /**
     * return the value of min.
     */
    private T min;

    /**
     * return the value of max.
     */
    private T max;

}

