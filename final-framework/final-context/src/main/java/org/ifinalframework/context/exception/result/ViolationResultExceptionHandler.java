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


import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.core.annotation.Order;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import org.ifinalframework.core.ResponseStatus;
import org.ifinalframework.core.result.R;
import org.ifinalframework.core.result.Result;

import jakarta.validation.ConstraintViolationException;

/**
 * The handler is to handle the exception throw by framework of {@link javax.validation.Validator}
 *
 * @author iimik
 * @version 1.0.0
 * @see ConstraintViolationException
 * @since 1.0.0
 */
@Order(0)
@Component
@ConditionalOnClass(ConstraintViolationException.class)
public class ViolationResultExceptionHandler implements ResultExceptionHandler<ConstraintViolationException> {

    @Override
    public boolean supports(@NonNull Throwable t) {

        return t instanceof ConstraintViolationException;
    }

    @NonNull
    @Override
    public Result<?> handle(@NonNull ConstraintViolationException e) {

        return R.failure(
                ResponseStatus.BAD_REQUEST.getCode(), e.getMessage()
        );
    }

}
