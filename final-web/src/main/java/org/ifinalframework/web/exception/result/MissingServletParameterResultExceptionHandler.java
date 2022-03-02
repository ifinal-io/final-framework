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

package org.ifinalframework.web.exception.result;

import org.ifinalframework.context.exception.result.ResultExceptionHandler;
import org.ifinalframework.core.result.R;
import org.ifinalframework.core.result.Result;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MissingServletRequestParameterException;

import java.util.Optional;

/**
 * 将 {@link MissingServletRequestParameterException}异常转化为{@link Result}结果，自定义描述语。
 *
 * @author likly
 * @version 1.0.0
 * @see MissingServletRequestParameterException
 * @since 1.0.0
 */
@Component
public class MissingServletParameterResultExceptionHandler implements
        ResultExceptionHandler<MissingServletRequestParameterException> {

    @Override
    public boolean supports(@NonNull Throwable throwable) {

        return throwable instanceof MissingServletRequestParameterException;
    }

    @Override
    @NonNull
    public Result<?> handle(@NonNull MissingServletRequestParameterException throwable) {
        return R.failure(400, Optional.ofNullable(throwable.getMessage()).orElse(""));
    }

}
