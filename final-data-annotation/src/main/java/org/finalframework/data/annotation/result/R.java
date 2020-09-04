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

package org.finalframework.data.annotation.result;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-26 21:11
 * @since 1.0
 */
public class R {

    private static final Integer SUCCESS_CODE = 0;
    private static final String SUCCESS_MESSAGE = "success";

    public static <T> Result<T> success() {
        return success(null);
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(SUCCESS_CODE, SUCCESS_MESSAGE, data);
    }

    public static <T> Result<T> success(@NonNull String code, @NonNull String message, @Nullable T data) {
        return new Result<>(SUCCESS_CODE, SUCCESS_MESSAGE, code, message, data);
    }

    public static Result<?> failure(@NonNull Integer status, @NonNull String description, @NonNull String code, @NonNull String message) {
        return new Result<>(status, description, code, message);
    }


    public static Result<?> failure(@NonNull Integer status, @NonNull String message) {
        return new Result<>(status, message);
    }

}
