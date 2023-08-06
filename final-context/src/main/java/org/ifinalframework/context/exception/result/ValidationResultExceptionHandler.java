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

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;

import org.ifinalframework.core.result.R;
import org.ifinalframework.core.result.Result;

import java.util.stream.Collectors;

/**
 * ValidationResultExceptionHandler.
 *
 * @author ilikly
 * @version 1.3.5
 * @see BindException
 * @see org.springframework.validation.ObjectError
 * @see FieldError
 * @since 1.3.5
 */
@Component
public class ValidationResultExceptionHandler implements ResultExceptionHandler<BindException> {
    @Override
    public boolean supports(@NonNull Throwable throwable) {
        return throwable instanceof BindException;
    }

    @NonNull
    @Override
    public Result<?> handle(@NonNull BindException e) {
        String message = e.getAllErrors().stream()
                .map(it -> {
                    if (it instanceof FieldError) {
                        return ((FieldError) it).getField() + " " + it.getDefaultMessage();
                    }
                    return it.getDefaultMessage();
                })
                .collect(Collectors.joining(","));

        return R.failure(400, message);
    }
}


