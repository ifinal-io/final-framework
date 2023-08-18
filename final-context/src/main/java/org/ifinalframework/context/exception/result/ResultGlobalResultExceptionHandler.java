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
import org.springframework.util.CollectionUtils;

import org.ifinalframework.context.exception.handler.GlobalExceptionHandler;
import org.ifinalframework.core.IException;
import org.ifinalframework.core.result.Result;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.List;

/**
 * @author ilikly
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
public class ResultGlobalResultExceptionHandler implements GlobalExceptionHandler<Result<?>> {

    private static final Logger logger = LoggerFactory.getLogger(ResultGlobalResultExceptionHandler.class);

    private final List<ResultExceptionHandler<?>> resultExceptionHandlers;

    private final ResultExceptionHandler<Throwable> defaultResultExceptionHandler = new UnCatchResultExceptionHandler();

    public ResultGlobalResultExceptionHandler(ObjectProvider<ResultExceptionHandler<?>> resultExceptionHandlers) {
        this.resultExceptionHandlers =  resultExceptionHandlers.stream().toList();
    }

    @Override
    public Result<?> handle(@NonNull Throwable throwable) {
        throwable = ExceptionUtils.getRootCause(throwable);

        if (!CollectionUtils.isEmpty(resultExceptionHandlers)) {
            for (ResultExceptionHandler resultExceptionHandler : resultExceptionHandlers) {
                if (resultExceptionHandler.supports(throwable)) {
                    if (throwable instanceof IException e) {
                        logger.warn("==> exception: code={},message={}", e.getCode(), e.getMessage());
                    } else {
                        logger.warn("==> exception: ", throwable);
                    }
                    final Result<?> result = (Result<?>) resultExceptionHandler.handle(throwable);
                    result.setTrace(MDC.get("trace"));
                    result.setTimestamp(System.currentTimeMillis());
                    result.setException(throwable.getClass());
                    return result;
                }
            }
        }

        logger.error("==> ", throwable);

        return defaultResultExceptionHandler.handle(throwable);

    }

}
