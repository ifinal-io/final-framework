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

package org.ifinalframework.security.web.servlet.interceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.ResolvableType;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import org.ifinalframework.core.IRepository;
import org.ifinalframework.data.annotation.DomainResource;
import org.ifinalframework.security.web.method.HandlerMethodPreAuthenticate;
import org.ifinalframework.web.annotation.servlet.Interceptor;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * HandlerMethodPreAuthenticateHandlerInterceptor.
 *
 * @author ilikly
 * @version 1.5.0
 * @since 1.5.0
 */
@Slf4j
@Interceptor
@ConditionalOnBean(HandlerMethodPreAuthenticate.class)
public class HandlerMethodPreAuthenticateHandlerInterceptor implements HandlerInterceptor, ApplicationContextAware, SmartInitializingSingleton {


    private final Map<String, RequestMappingInfo> requestMappingInfoMap = new LinkedHashMap<>();
    @Setter
    private ApplicationContext applicationContext;
    @Resource
    private HandlerMethodPreAuthenticate handlerMethodPreAuthenticate;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (!(handler instanceof HandlerMethod)) {
            return HandlerInterceptor.super.preHandle(request, response, handler);
        }

        @SuppressWarnings("unchecked")
        Map<String, String> uriTemplateVars =
                (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        logger.info("uriTemplateVars={}", uriTemplateVars);

        RequestMappingInfo requestMappingInfo = requestMappingInfoMap.get(handler.toString());


        if (Objects.isNull(requestMappingInfo)) {
            return HandlerInterceptor.super.preHandle(request, response, handler);
        }


        Set<String> patternValues = requestMappingInfo.getPathPatternsCondition().getPatternValues();

        final String resource = uriTemplateVars.get("resource");
        if (Objects.nonNull(resource)) {
            patternValues = patternValues.stream().map(it -> it.replace("{resource}", resource)).collect(Collectors.toSet());
        }


        handlerMethodPreAuthenticate.authenticate((HandlerMethod) handler, HttpMethod.resolve(request.getMethod()), patternValues);

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void afterSingletonsInstantiated() {
        RequestMappingHandlerMapping contextBean = applicationContext.getBean(RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = contextBean.getHandlerMethods();

        List<String> resources = applicationContext.getBeanProvider(IRepository.class).stream()
                .filter(it -> AnnotatedElementUtils.isAnnotated(AopUtils.getTargetClass(it), Service.class))
                .map(it -> ResolvableType.forClass(AopUtils.getTargetClass(it)).as(IRepository.class).resolveGeneric(1))
                .filter(Objects::nonNull)
                .map(it -> AnnotatedElementUtils.getMergedAnnotation(it, DomainResource.class))
                .filter(Objects::nonNull)
                .flatMap(it -> Arrays.stream(it.value()))
                .collect(Collectors.toList());

        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : handlerMethods.entrySet()) {
            HandlerMethod handlerMethod = entry.getValue();
            RequestMappingInfo requestMappingInfo = entry.getKey();
            requestMappingInfoMap.put(handlerMethod.toString(), requestMappingInfo);
        }
    }

}
