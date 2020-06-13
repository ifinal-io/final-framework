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

import io.lettuce.core.RedisConnectionException;
import org.finalframework.data.exception.handler.ExceptionHandler;
import org.finalframework.data.exception.result.ResultExceptionHandler;
import org.finalframework.data.result.R;
import org.finalframework.data.result.Result;
import org.finalframework.spring.annotation.factory.SpringComponent;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;

/**
 * @author likly
 * @version 1.0
 * @date 2019-09-25 11:31:21
 * @since 1.0
 */
@SpringComponent
@ConditionalOnClass(RedisConnectionException.class)
public class RedisConnectExceptionHandler implements ResultExceptionHandler<RedisConnectionException> {
    @Override
    public boolean supports(Throwable throwable) {
        return throwable instanceof RedisConnectionException;
    }

    @Override
    public Result<?> handle(RedisConnectionException throwable) {
        return R.failure(500, "Redis 连接异常： " + throwable.getMessage());
    }
}
