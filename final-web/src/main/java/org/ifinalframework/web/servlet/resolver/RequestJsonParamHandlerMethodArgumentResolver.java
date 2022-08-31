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

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Optional;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ValueConstants;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import org.ifinalframework.context.exception.BadRequestException;
import org.ifinalframework.json.Json;
import org.ifinalframework.util.Asserts;
import org.ifinalframework.web.annotation.bind.RequestJsonParam;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * A custom {@link HandlerMethodArgumentResolver} for {@link RequestJsonParam} annotation which supports the json format
 * request param like {@link RequestParam}, also supports {@link RequestBody}.
 *
 * @author ilikly
 * @version 1.0.0
 * @see RequestJsonParam
 * @see RequestBody
 * @see org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor
 * @since 1.0.0
 */
@Slf4j
public final class RequestJsonParamHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Setter
    private Charset defaultCharset = StandardCharsets.UTF_8;

    /**
     * return {@code true} if the {@linkplain MethodParameter parameter} is annotated by {@link RequestJsonParam},
     * otherwise {@code false}.
     *
     * @param parameter the method parameter
     * @return {@code true} if the {@linkplain MethodParameter parameter} is annotated by {@link RequestJsonParam},
     * otherwise {@code false}.
     */
    @Override
    public boolean supportsParameter(@NonNull MethodParameter parameter) {
        // support the method parameter annotated by @RequestJsonParam or @RequestBody
        return parameter.hasParameterAnnotation(RequestBody.class) || parameter.hasParameterAnnotation(RequestJsonParam.class);
    }

    @Override
    public Object resolveArgument(@NonNull MethodParameter parameter,
                                  @Nullable ModelAndViewContainer mavContainer,
                                  @NonNull NativeWebRequest webRequest,
                                  @Nullable WebDataBinderFactory binderFactory) throws Exception {

        final String contentType = webRequest.getHeader("content-type");

        String value;

        if (Objects.nonNull(contentType) && contentType.startsWith("application/json")) {
            value = parseBody(webRequest);
        } else {
            value = parseParameter(parameter, webRequest);
        }

        if (Objects.isNull(value)) {
            return null;
        }

        if (logger.isDebugEnabled()) {
            logger.debug("parse RequestJsonParam name={},value={},", parameter.getParameterName(), value);
        }

        return parseJson(value, parameter);

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

    private String parseParameter(@NonNull MethodParameter parameter,
                                  @NonNull NativeWebRequest webRequest) {
        // find the @RequestJsonParam annotation
        final RequestJsonParam requestJsonParam = parameter.getParameterAnnotation(RequestJsonParam.class);
        final boolean required = Optional.ofNullable(requestJsonParam).map(RequestJsonParam::required).orElse(true);

        final String parameterName = getParameterName(requestJsonParam, parameter);
        Objects.requireNonNull(parameterName);
        String value = webRequest.getParameter(parameterName);
        if (Asserts.isBlank(value) && required) {
            throw new BadRequestException(String.format("parameter %s is required", parameterName));
        }

        if (Asserts.isBlank(value) && (requestJsonParam != null && !ValueConstants.DEFAULT_NONE.equals(requestJsonParam.defaultValue()))) {
            value = requestJsonParam.defaultValue();
        }

        if (Asserts.isBlank(value)) {
            return null;
        }

        return value;
    }


    private Object parseJson(final String json, final MethodParameter parameter) {
        return Json.toObject(json, parameter.getGenericParameterType());
    }

    /**
     * 获取指定的参数名，如果{@link RequestJsonParam#value()}未指定，则使用{@link MethodParameter#getParameterName()}。
     */
    private String getParameterName(final RequestJsonParam requestJsonParam, final MethodParameter parameter) {

        if (Objects.isNull(requestJsonParam)) {
            return "requestBody";
        }

        return Asserts.isEmpty(requestJsonParam.value()) ? parameter.getParameterName()
                : requestJsonParam.value().trim();
    }

    @NonNull
    private Charset getContentTypeCharset(@Nullable MediaType contentType) {

        if (contentType != null && contentType.getCharset() != null) {
            return Optional.ofNullable(contentType.getCharset()).orElse(getDefaultCharset());
        } else {
            return getDefaultCharset();
        }
    }

    @NonNull
    public Charset getDefaultCharset() {
        return this.defaultCharset;
    }

}
