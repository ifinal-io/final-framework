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

package org.ifinalframework.data.web.servlet.method.annotation;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.HandlerMapping;

import org.ifinalframework.data.domain.DomainService;
import org.ifinalframework.data.domain.DomainServiceRegistry;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

import lombok.extern.slf4j.Slf4j;

/**
 * DomainServiceHandlerMethodArgumentResolver.
 *
 * @author iimik
 * @version 1.5.1
 * @see DomainService
 * @since 1.5.1
 */
@Slf4j
@Component
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class DomainServiceHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Resource
    private DomainServiceRegistry domainServiceRegistry;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType() == DomainService.class;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        if (logger.isDebugEnabled()) {
            final Object nativeRequest = webRequest.getNativeRequest();
            if (nativeRequest instanceof HttpServletRequest request) {
                logger.debug("==> {} {}", request.getMethod(), request.getRequestURI());
            }
        }
        return domainServiceRegistry.getDomainService(resolveName("resource", parameter, webRequest));
    }

    protected String resolveName(String name, MethodParameter parameter, NativeWebRequest request) throws Exception {
        Map<String, String> uriTemplateVars = (Map<String, String>) request.getAttribute(
                HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE, RequestAttributes.SCOPE_REQUEST);
        return (uriTemplateVars != null ? uriTemplateVars.get(name) : null);
    }
}
