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

import org.finalframework.context.exception.result.ResultExceptionHandler;
import org.finalframework.data.annotation.result.R;
import org.finalframework.data.annotation.result.Result;
import org.finalframework.auto.spring.factory.annotation.SpringComponent;
import org.springframework.web.bind.MissingServletRequestParameterException;

/**
 * 将 {@link MissingServletRequestParameterException}异常转化为{@link Result}结果，自定义描述语。
 *
 * @author likly
 * @version 1.0
 * @date 2019-09-03 15:29:32
 * @see MissingServletRequestParameterException
 * @since 1.0
 */
@SpringComponent
public class MissingServletParameterResultExceptionHandler implements ResultExceptionHandler<MissingServletRequestParameterException> {
    @Override
    public boolean supports(Throwable throwable) {
        return throwable instanceof MissingServletRequestParameterException;
    }

    @Override
    public Result<?> handle(MissingServletRequestParameterException throwable) {
        MissingServletRequestParameterException e = throwable;
        return R.failure(400, e.getMessage());
    }
}
