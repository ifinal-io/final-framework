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

package org.finalframework.data.exception.result;


import org.finalframework.data.exception.IException;
import org.finalframework.data.exception.UnCatchException;
import org.finalframework.data.exception.handler.GlobalExceptionHandler;
import org.finalframework.data.result.Result;
import org.finalframework.spring.annotation.factory.SpringComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2019-04-15 11:09:58
 * @since 1.0
 */
@SpringComponent
@ConditionalOnMissingBean(GlobalExceptionHandler.class)
public class ResultGlobalResultExceptionHandler implements GlobalExceptionHandler<Result<?>> {
    private static final Logger logger = LoggerFactory.getLogger(ResultGlobalResultExceptionHandler.class);
    private List<ResultExceptionHandler<?>> resultExceptionHandlers = new ArrayList<>();

    public ResultGlobalResultExceptionHandler(ObjectProvider<List<ResultExceptionHandler<?>>> resultExceptionHandlerObjectProvider) {
        this.resultExceptionHandlers.addAll(resultExceptionHandlerObjectProvider.getIfAvailable());
    }

    @Override
    public Result<?> handle(Throwable throwable) {

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
