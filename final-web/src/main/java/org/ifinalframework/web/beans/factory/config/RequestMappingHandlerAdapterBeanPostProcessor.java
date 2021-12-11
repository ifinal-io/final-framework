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

package org.ifinalframework.web.beans.factory.config;

import org.ifinalframework.web.resolver.RequestJsonParamHandlerMethodArgumentResolver;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.mvc.method.annotation.AbstractMessageConverterMethodArgumentResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Add {@link RequestJsonParamHandlerMethodArgumentResolver} before the build-in.
 *
 * @author likly
 * @version 1.2.3
 * @see RequestJsonParamHandlerMethodArgumentResolver
 * @see RequestMappingHandlerAdapter
 * @since 1.2.3
 */
@Component
public class RequestMappingHandlerAdapterBeanPostProcessor implements BeanPostProcessor {

    /**
     * @see AbstractMessageConverterMethodArgumentResolver#messageConverters
     */
    private static final Field MESSAGE_CONVERTERS = Objects.requireNonNull(
            ReflectionUtils.findField(AbstractMessageConverterMethodArgumentResolver.class, "messageConverters"),
            "Can not found messageConverters in class of AbstractMessageConverterMethodArgumentResolver.");

    static {
        ReflectionUtils.makeAccessible(MESSAGE_CONVERTERS);
    }


    @Nullable
    @Override
    public Object postProcessAfterInitialization(@NonNull Object bean, @NonNull String beanName) throws BeansException {

        if (bean instanceof RequestMappingHandlerAdapter) {

            final RequestMappingHandlerAdapter adapter = (RequestMappingHandlerAdapter) bean;

            final List<HandlerMethodArgumentResolver> defaultArgumentResolvers
                    = Optional.ofNullable(adapter.getArgumentResolvers()).orElse(Collections.emptyList());

            final List<HandlerMethodArgumentResolver> argumentResolvers = new ArrayList<>(defaultArgumentResolvers.size() + 1);

            argumentResolvers.add(new RequestJsonParamHandlerMethodArgumentResolver());

            for (HandlerMethodArgumentResolver argumentResolver : defaultArgumentResolvers) {
                if (argumentResolver instanceof AbstractMessageConverterMethodArgumentResolver) {
                    ReflectionUtils.setField(MESSAGE_CONVERTERS, argumentResolver, adapter.getMessageConverters());

                }
                argumentResolvers.add(argumentResolver);
            }

            adapter.setArgumentResolvers(argumentResolvers);

        }


        return bean;

    }
}
