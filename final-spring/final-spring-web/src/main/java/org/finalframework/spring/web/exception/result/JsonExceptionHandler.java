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

package org.finalframework.spring.web.exception.result;

import org.finalframework.data.exception.result.ResultExceptionHandler;
import org.finalframework.data.result.R;
import org.finalframework.data.result.Result;
import org.finalframework.json.JsonException;
import org.finalframework.spring.annotation.factory.SpringComponent;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.lang.NonNull;

/**
 * Json 异常处理器，将业务层抛出的{@link JsonException}异常包装成 {@link Result}返回给前端。
 *
 * @author likly
 * @version 1.0
 * @date 2018-10-31 13:20
 * @see JsonException
 * @since 1.0
 */
@SpringComponent
@ConditionalOnClass(JsonException.class)
public class JsonExceptionHandler implements ResultExceptionHandler<JsonException> {
    @Override
    public boolean supports(@NonNull Throwable t) {
        return t instanceof JsonException;
    }

    @NonNull
    @Override
    public Result<?> handle(JsonException e) {
        return R.failure(400, e.getMessage());
    }
}
