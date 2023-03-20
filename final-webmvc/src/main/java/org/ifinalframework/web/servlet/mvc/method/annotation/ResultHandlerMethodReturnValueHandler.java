/*
 * Copyright 2020-2023 the original author or authors.
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

package org.ifinalframework.web.servlet.mvc.method.annotation;

import lombok.RequiredArgsConstructor;
import org.ifinalframework.context.result.ResultFunctionConsumerComposite;
import org.ifinalframework.core.result.Result;
import org.springframework.core.MethodParameter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

/**
 * ResultHandlerMethodReturnValueHandler.
 *
 * @author ilikly
 * @version 1.0.0
 * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurer
 * @since 1.0.0
 */
@RequiredArgsConstructor
public class ResultHandlerMethodReturnValueHandler implements HandlerMethodReturnValueHandler {

    private final ResultFunctionConsumerComposite resultFunctionConsumerComposite;

    private final RequestResponseBodyMethodProcessor handler;


    @Override
    public boolean supportsReturnType(final @NonNull MethodParameter returnType) {
        return handler.supportsReturnType(returnType);
    }

    @Override
    public void handleReturnValue(@Nullable Object returnValue, @NonNull MethodParameter returnType,
                                  @NonNull ModelAndViewContainer mavContainer, @NonNull NativeWebRequest webRequest)
            throws Exception {

        final Result<?> convert = resultFunctionConsumerComposite.apply(returnValue);

        handler.handleReturnValue(convert, returnType, mavContainer, webRequest);

    }

}
