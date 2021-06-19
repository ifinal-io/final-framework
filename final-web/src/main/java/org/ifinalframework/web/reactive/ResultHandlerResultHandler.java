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

package org.ifinalframework.web.reactive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.MethodParameter;
import org.springframework.core.Ordered;
import org.springframework.core.ReactiveAdapterRegistry;
import org.springframework.core.ResolvableType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.reactive.HandlerResult;
import org.springframework.web.reactive.accept.RequestedContentTypeResolver;
import org.springframework.web.reactive.config.WebFluxConfigurationSupport;
import org.springframework.web.reactive.result.method.annotation.ResponseBodyResultHandler;
import org.springframework.web.server.ServerWebExchange;

import org.ifinalframework.context.converter.result.Object2ResultConverter;
import org.ifinalframework.web.annotation.condition.ConditionalOnReactiveWebApplication;

import java.lang.reflect.Type;

import reactor.core.publisher.Mono;

/**
 * 将{@link HandlerMethod}的返回结果 {@link HandlerResult#getReturnValue()}进行统一的包装。
 *
 * @author likly
 * @version 1.0.0
 * @see ResponseBodyResultHandler
 * @see WebFluxConfigurationSupport
 * @since 1.0.0
 */
@Component
@ConditionalOnReactiveWebApplication
public class ResultHandlerResultHandler extends ResponseBodyResultHandler {

    private static final Object2ResultConverter OBJECT_2_RESULT_CONVERTER = new Object2ResultConverter();

    /**
     * @param reactiveAdapterRegistry
     * @param serverCodecConfigurer
     * @param contentTypeResolver
     * @see WebFluxConfigurationSupport#responseBodyResultHandler(ReactiveAdapterRegistry, ServerCodecConfigurer,
     *     RequestedContentTypeResolver)
     */
    @Autowired
    public ResultHandlerResultHandler(
        @Qualifier("webFluxAdapterRegistry") ReactiveAdapterRegistry reactiveAdapterRegistry,
        ServerCodecConfigurer serverCodecConfigurer,
        @Qualifier("webFluxContentTypeResolver") RequestedContentTypeResolver contentTypeResolver) {
        super(serverCodecConfigurer.getWriters(), contentTypeResolver, reactiveAdapterRegistry);
        setOrder(Ordered.HIGHEST_PRECEDENCE);
    }

    @Override
    public Mono<Void> handleResult(final ServerWebExchange exchange, final HandlerResult result) {
        Object body = OBJECT_2_RESULT_CONVERTER.convert(result.getReturnValue());
        MethodParameter bodyTypeParameter = result.getReturnTypeSource();
        final ReturnValueMethodParameter actualParam = new ReturnValueMethodParameter(bodyTypeParameter, body);
        return writeBody(body, actualParam, actualParam, exchange);
    }

    private static class ReturnValueMethodParameter extends MethodParameter {

        private final Object returnValue;

        public ReturnValueMethodParameter(final MethodParameter origin, final Object returnValue) {
            super(origin.getMethod(), origin.getParameterIndex());
            this.returnValue = returnValue;
        }

        @NonNull
        @Override
        public Class<?> getParameterType() {
            return (this.returnValue != null ? this.returnValue.getClass() : super.getParameterType());
        }

        @NonNull
        @Override
        public Type getGenericParameterType() {
            return ResolvableType.forClass(getParameterType()).getType();
        }

    }

}
