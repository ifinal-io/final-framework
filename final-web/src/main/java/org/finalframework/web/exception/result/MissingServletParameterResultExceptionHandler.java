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
 */

package org.finalframework.web.exception.result;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.MissingServletRequestParameterException;

import org.finalframework.context.exception.result.ResultExceptionHandler;
import org.finalframework.core.result.R;
import org.finalframework.core.result.Result;

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
    public boolean supports(final Throwable throwable) {

        return throwable instanceof MissingServletRequestParameterException;
    }

    @Override
    public Result<?> handle(final MissingServletRequestParameterException throwable) {

        MissingServletRequestParameterException e = throwable;
        return R.failure(400, e.getMessage());
    }

}
