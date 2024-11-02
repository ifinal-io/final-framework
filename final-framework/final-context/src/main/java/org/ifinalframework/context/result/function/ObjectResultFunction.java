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

package org.ifinalframework.context.result.function;

import org.springframework.core.annotation.Order;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import org.ifinalframework.context.result.ResultFunction;
import org.ifinalframework.core.result.R;
import org.ifinalframework.core.result.Result;

import java.io.Serializable;

/**
 * ObjectResultFunction.
 *
 * @author iimik
 * @version 1.2.1
 * @since 1.2.1
 */
@Order
@Component
public class ObjectResultFunction implements ResultFunction {


    @NonNull
    @Override
    public Result<?> apply(@Nullable Object body) {
        if (body == null) {
            return R.success();
        }

        if (body instanceof Result) {
            return (Result<?>) body;
        }

        if (body instanceof Serializable) {
            return R.success(body);
        }

        throw new IllegalArgumentException(body.getClass() + " must impl Serializable");
    }
}
