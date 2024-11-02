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

import org.springframework.beans.BeanUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.bind.support.WebRequestDataBinder;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.method.annotation.ExtendedServletRequestDataBinder;

import org.ifinalframework.context.exception.BadRequestException;
import org.ifinalframework.data.domain.action.DomainActionRegistry;
import org.ifinalframework.data.domain.action.DomainActions;
import org.ifinalframework.json.Json;
import org.ifinalframework.web.annotation.bind.RequestEntity;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import lombok.Getter;
import lombok.Setter;

/**
 * RequestDomainBodyHandlerMethodArgumentResolver
 *
 * @author iimik
 * @since 1.5.2
 **/
@Configuration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class RequestEntityHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Setter
    @Getter
    private Charset defaultCharset = StandardCharsets.UTF_8;
    @Resource
    private DomainActionRegistry domainActionRegistry;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(RequestEntity.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        final RequestEntity requestDomainBody = parameter.getParameterAnnotation(RequestEntity.class);
        final String resource = resolveName(requestDomainBody.value(), parameter, webRequest);
        final DomainActions domainActions = domainActionRegistry.get(resource);
        Class<?> domainEntityClass = domainActions.getDomainEntityClasses().get(requestDomainBody.view());
        if (domainEntityClass == null) {
            domainEntityClass = domainActions.getEntityClass();
        }
        final String contentType = webRequest.getHeader(HttpHeaders.CONTENT_TYPE);
        Object value = null;
        if (Objects.nonNull(contentType) && contentType.startsWith("application/json")) {
            String body = parseBody(webRequest);
            if (Objects.isNull(body)) {
                throw new BadRequestException("requestBody is null");
            }
            if (body.startsWith("[")) {
                value = Json.toList(body, domainEntityClass);
            } else {
                value = Json.toObject(body, domainEntityClass);
            }
        } else {
            value = BeanUtils.instantiateClass(domainEntityClass);
            WebDataBinder binder = binderFactory.createBinder(webRequest, value, "entity");
            bindEntity(webRequest, binder);
        }

        return value;
    }

    private String parseBody(@NonNull NativeWebRequest webRequest) throws IOException {

        final Object nativeRequest = webRequest.getNativeRequest();
        if (nativeRequest instanceof HttpServletRequest) {
            ServletServerHttpRequest inputMessage = new ServletServerHttpRequest((HttpServletRequest) nativeRequest);
            Charset charset = getContentTypeCharset(inputMessage.getHeaders().getContentType());
            return StreamUtils.copyToString(inputMessage.getBody(), charset).trim();
        }
        return null;
    }


    private void bindEntity(NativeWebRequest request, WebDataBinder binder) throws Exception {
        if (binder instanceof WebRequestDataBinder) {
            ((WebRequestDataBinder) binder).bind(request);
        } else if (binder instanceof ExtendedServletRequestDataBinder && request.getNativeRequest() instanceof ServletRequest) {
            ((ExtendedServletRequestDataBinder) binder).bind((ServletRequest) request.getNativeRequest());
        }

    }

    @NonNull
    private Charset getContentTypeCharset(@Nullable MediaType contentType) {

        if (contentType != null && contentType.getCharset() != null) {
            return Optional.ofNullable(contentType.getCharset()).orElse(getDefaultCharset());
        } else {
            return getDefaultCharset();
        }
    }

    protected String resolveName(String name, MethodParameter parameter, NativeWebRequest request) throws Exception {
        Map<String, String> uriTemplateVars = (Map<String, String>) request.getAttribute(
                HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE, RequestAttributes.SCOPE_REQUEST);
        return (uriTemplateVars != null ? uriTemplateVars.get(name) : null);
    }
}
