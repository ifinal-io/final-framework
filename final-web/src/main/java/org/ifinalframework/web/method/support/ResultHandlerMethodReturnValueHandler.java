/*
 * Copyright 2020-2021 the original author or authors.
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

package org.ifinalframework.web.method.support;

import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import org.ifinalframework.context.converter.result.Object2ResultConverter;
import org.ifinalframework.core.result.Result;

/**
 * ResultHandlerMethodReturnValueHandler.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class ResultHandlerMethodReturnValueHandler implements HandlerMethodReturnValueHandler {

    private final Object2ResultConverter converter = new Object2ResultConverter();

    private final RequestResponseBodyMethodProcessor handler;

    public ResultHandlerMethodReturnValueHandler(
        final RequestResponseBodyMethodProcessor handler) {
        this.handler = handler;
    }

    @Override
    public boolean supportsReturnType(final MethodParameter returnType) {
        return handler.supportsReturnType(returnType);
    }

    @Override
    public void handleReturnValue(@Nullable final Object returnValue, final MethodParameter returnType,
        final ModelAndViewContainer mavContainer, final NativeWebRequest webRequest) throws Exception {

        final Result<?> convert = converter.convert(returnValue);

        handler.handleReturnValue(convert, returnType, mavContainer, webRequest);

    }

}
