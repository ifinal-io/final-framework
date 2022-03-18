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

package org.ifinalframework.context.exception.result;

import org.ifinalframework.core.result.R;
import org.ifinalframework.core.result.Result;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author ilikly
 * @version 1.0.0
 * @see DuplicateKeyException
 * @since 1.0.0
 */
@Order(0)
@Component
@ConditionalOnClass(DuplicateKeyException.class)
public class DuplicateKeyResultExceptionHandler implements ResultExceptionHandler<DuplicateKeyException> {

    @Override
    public boolean supports(@NonNull Throwable throwable) {
        return throwable instanceof DuplicateKeyException;
    }

    @Override
    @NonNull
    public Result<?> handle(@NonNull DuplicateKeyException throwable) {
        return R.failure(500, "Duplicate Key", "500", Optional.ofNullable(throwable.getMessage()).orElse(""));

    }

}

