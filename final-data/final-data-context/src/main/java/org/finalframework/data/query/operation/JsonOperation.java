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

package org.finalframework.data.query.operation;


import org.finalframework.data.query.operation.function.*;
import org.springframework.lang.NonNull;

/**
 * @author likly
 * @version 1.0
 * @date 2020-03-31 20:29:21
 * @since 1.0
 */
public enum JsonOperation implements Operation {
    JSON_EXTRACT,
    JSON_CONTAINS,
    NOT_JSON_CONTAINS,
    JSON_UNQUOTE,
    JSON_KEYS,
    JSON_DEPTH,
    JSON_LENGTH,
    JSON_OBJECT,
    JSON_ARRAY,

    ;

    public static Function contains(@NonNull Object value, @NonNull String path) {
        return new JsonContainsFunctionOperation(value, path);
    }

    public static Function extract(@NonNull String path) {
        return new JsonExtractFunctionOperation(path);
    }

    public static Function unquote() {
        return new JsonUnquoteFunctionOperation();
    }


    public static Function keys() {
        return new JsonKeysFunctionOperation();
    }


    public static Function depth() {
        return new JsonDepthFunctionOperation();
    }


    public static Function length() {
        return new JsonLengthFunctionOperation();
    }

    public static Function object() {
        return new JsonObjectFunctionOperation();
    }

    public static Function array() {
        return new JsonArrayFunctionOperation();
    }


}

