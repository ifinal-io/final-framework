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
import org.springframework.util.ClassUtils;
import org.springframework.util.StreamUtils;
import org.springframework.validation.BindException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.bind.support.WebRequestDataBinder;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.method.annotation.ExtendedServletRequestDataBinder;

import org.ifinalframework.core.IQuery;
import org.ifinalframework.data.domain.action.DomainActionRegistry;
import org.ifinalframework.data.domain.action.DomainActions;
import org.ifinalframework.json.Json;
import org.ifinalframework.validation.GlobalValidationGroupsProvider;
import org.ifinalframework.web.annotation.bind.RequestQuery;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import lombok.Getter;
import lombok.Setter;

/**
 * RequestQueryHandlerMethodArgumentResolver.
 *
 * @author iimik
 * @version 1.5.0
 * @since 1.5.0
 */
@Configuration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class RequestQueryHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Setter
    @Getter
    private Charset defaultCharset = StandardCharsets.UTF_8;
    @Resource
    private DomainActionRegistry domainActionRegistry;
    @Resource
    private GlobalValidationGroupsProvider globalValidationGroupsProvider;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(RequestQuery.class);
    }

    @Nullable
    @Override
    public Object resolveArgument(MethodParameter parameter, @Nullable ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, @Nullable WebDataBinderFactory binderFactory) throws Exception {
        final RequestQuery requestQuery = parameter.getParameterAnnotation(RequestQuery.class);
        final String resource = resolveName(requestQuery.resource(), parameter, webRequest);
        final DomainActions domainActions = domainActionRegistry.get(resource);
        final Class<? extends IQuery> domainQueryClass = (Class<? extends IQuery>) domainActions
                .getDomainQueryClasses().get(requestQuery.view());
        final String contentType = webRequest.getHeader(HttpHeaders.CONTENT_TYPE);
        IQuery value = null;
        WebDataBinder binder = null;
        if (Objects.nonNull(contentType) && contentType.startsWith("application/json")) {
            String body = parseBody(webRequest);
            value = Json.toObject(body, domainQueryClass);
            binder = binderFactory.createBinder(webRequest, value, "query");
        } else {
            value = BeanUtils.instantiateClass(domainQueryClass);
            binder = binderFactory.createBinder(webRequest, value, "query");
            bindQuery(webRequest, binder);
        }

        List<Class<?>> validationGroups = new LinkedList<>(globalValidationGroupsProvider.getValidationGroups(requestQuery.view()));
        binder.validate(ClassUtils.toClassArray(validationGroups));
        if (binder.getBindingResult().hasErrors()) {
            throw new BindException(binder.getBindingResult());
        }

        return value;
    }

    private String parseBody(@NonNull NativeWebRequest webRequest) throws IOException {

        final Object nativeRequest = webRequest.getNativeRequest();
        if (nativeRequest instanceof HttpServletRequest) {
            ServletServerHttpRequest inputMessage = new ServletServerHttpRequest((HttpServletRequest) nativeRequest);
            Charset charset = getContentTypeCharset(inputMessage.getHeaders().getContentType());
            return StreamUtils.copyToString(inputMessage.getBody(), charset);
        }
        return null;
    }


    private void bindQuery(NativeWebRequest request, WebDataBinder binder) throws Exception {
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
