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

package org.ifinalframework.context.exception.result;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import org.ifinalframework.context.exception.UnCatchException;
import org.ifinalframework.context.exception.handler.GlobalExceptionHandler;
import org.ifinalframework.core.IException;
import org.ifinalframework.core.result.Result;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

/**
 * @author ilikly
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
//@ConditionalOnMissingBean(GlobalExceptionHandler.class)
public class ResultGlobalResultExceptionHandler implements GlobalExceptionHandler<Result<?>> {

    private static final Logger logger = LoggerFactory.getLogger(ResultGlobalResultExceptionHandler.class);

    private final List<ResultExceptionHandler<?>> resultExceptionHandlers = new ArrayList<>();

    public ResultGlobalResultExceptionHandler(
        final ObjectProvider<List<ResultExceptionHandler<?>>> resultExceptionHandlerObjectProvider) {

        this.resultExceptionHandlers.addAll(resultExceptionHandlerObjectProvider.getIfAvailable());
    }

    @Override
    public Result<?> handle(final @NonNull Throwable throwable) {

        if (throwable instanceof IException) {
            final IException e = (IException) throwable;
            logger.warn("==> exception: code={},message={}", e.getCode(), e.getMessage());
        } else {
            logger.error("==> ", throwable);
        }

        for (ResultExceptionHandler<?> resultExceptionHandler : resultExceptionHandlers) {
            if (resultExceptionHandler.supports(throwable)) {
                final Result<?> result = ((ResultExceptionHandler<Throwable>) resultExceptionHandler).handle(throwable);
                result.setTrace(MDC.get("trace"));
                result.setTimestamp(System.currentTimeMillis());
                result.setException(throwable.getClass());
                return result;
            }
        }

        throw new UnCatchException(throwable);

    }

}
