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

package org.ifinalframework.web.servlet.resolver;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import org.ifinalframework.context.exception.UnauthorizedException;
import org.ifinalframework.context.user.UserSupplier;
import org.ifinalframework.core.IUser;

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Resolved the argument of {@link IUser}.
 *
 * @author ilikly
 * @version 1.4.1
 * @since 1.4.1
 */
@Component
public class IUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    private final List<UserSupplier> userSuppliers;

    public IUserHandlerMethodArgumentResolver(ObjectProvider<UserSupplier> provider) {
        this.userSuppliers = provider.orderedStream().collect(Collectors.toList());
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return IUser.class.isAssignableFrom(parameter.getParameterType()) && !parameter.hasParameterAnnotation(RequestBody.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        return userSuppliers.stream()
                .map(Supplier::get)
                .filter(Objects::nonNull)
                .findFirst()
                .orElseThrow(() -> new UnauthorizedException("用户未登录"));
    }
}


