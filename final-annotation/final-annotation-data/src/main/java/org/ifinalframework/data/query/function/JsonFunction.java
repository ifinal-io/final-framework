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

package org.ifinalframework.data.query.function;

import org.springframework.lang.NonNull;

import org.ifinalframework.data.query.Criteriable;
import org.ifinalframework.data.query.condition.JsonCondition;

/**
 * JsonFunction.
 *
 * @author iimik
 * @version 1.3.1
 * @since 1.3.1
 * @see JsonCondition
 */
public interface JsonFunction<V> extends Function<V> {

    default Criteriable<V> jsonExtract(@NonNull String path) {
        return apply(column -> String.format("JSON_EXTRACT(%s, #{${criterion}.path})", column),
                criterion -> criterion.put("path", path));
    }

    /**
     * JSON_UNQUOTE
     *
     * @return criterion
     * @since 1.3.5
     */
    default Criteriable<V> jsonUnquote() {
        return apply(column -> String.format("JSON_UNQUOTE(%s)", column));
    }
}
