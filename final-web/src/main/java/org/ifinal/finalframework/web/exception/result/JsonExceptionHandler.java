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

package org.ifinal.finalframework.web.exception.result;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import org.ifinal.finalframework.context.exception.result.ResultExceptionHandler;
import org.ifinal.finalframework.core.result.R;
import org.ifinal.finalframework.core.result.Result;
import org.ifinal.finalframework.json.JsonException;

/**
 * Json 异常处理器，将业务层抛出的{@link JsonException}异常包装成 {@link Result}返回给前端。
 *
 * @author likly
 * @version 1.0.0
 * @see JsonException
 * @since 1.0.0
 */
@Component
@ConditionalOnClass(JsonException.class)
public class JsonExceptionHandler implements ResultExceptionHandler<JsonException> {

    @Override
    public boolean supports(final @NonNull Throwable t) {

        return t instanceof JsonException;
    }

    @NonNull
    @Override
    public Result<?> handle(final JsonException e) {

        return R.failure(400, e.getMessage());
    }

}
