package org.finalframework.spring.web.resolver;

import org.finalframework.core.Assert;
import org.finalframework.data.exception.BadRequestException;
import org.finalframework.json.Json;
import org.finalframework.spring.web.resolver.annotation.ArgumentResolver;
import org.finalframework.spring.web.resolver.annotation.RequestJsonParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.lang.Nullable;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.ValueConstants;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.Charset;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-31 11:22:47
 * @see RequestJsonParam
 * @since 1.0
 */
@ArgumentResolver
public class RequestJsonParamHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    private static final Logger logger = LoggerFactory.getLogger(RequestJsonParamHandlerMethodArgumentResolver.class);

    private Charset defaultCharset = Charset.defaultCharset();

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(RequestJsonParam.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        RequestJsonParam requestJsonParam = parameter.getParameterAnnotation(RequestJsonParam.class);
        if (Assert.isNull(requestJsonParam)) {
            // it can not enter
            throw new NullPointerException("requestJsonParam annotation is null");
        }

        final String contentType = webRequest.getHeader("content-type");
        if (Assert.nonEmpty(contentType) && contentType.startsWith("application/json")) {

            final Object nativeRequest = webRequest.getNativeRequest();
            if (nativeRequest instanceof HttpServletRequest) {
                ServletServerHttpRequest inputMessage = new ServletServerHttpRequest((HttpServletRequest) nativeRequest);
                Charset charset = getContentTypeCharset(inputMessage.getHeaders().getContentType());
                final String body = StreamUtils.copyToString(inputMessage.getBody(), charset);
                if (Assert.nonEmpty(body)) {
                    logger.debug("==> jsonBody: {}", body);
                    return parseJson(body, parameter);
                }
            }

            return null;

        } else {
            final String parameterName = getParameterName(requestJsonParam, parameter);
            String value = webRequest.getParameter(parameterName);
            if (Assert.isBlank(value) && requestJsonParam.required()) {
                throw new BadRequestException(String.format("parameter %s is required", parameterName));
            }

            if (Assert.isBlank(value) && !ValueConstants.DEFAULT_NONE.equals(requestJsonParam.defaultValue())) {
                value = requestJsonParam.defaultValue();
            }

            if (Assert.isBlank(value)) return null;
            logger.debug("==> RequestJsonParam: name={},value={}", parameterName, value);
            return parseJson(value, parameter);
        }

    }

    private Object parseJson(String json, MethodParameter parameter) {
        final Class<?> type = parameter.getParameterType();
        final RequestJsonParamHandler<?> requestJsonParamHandler = RequestJsonParamHandlerRegistry.getInstance().getRequestJsonParamHandler(type);
        if (requestJsonParamHandler != null) {
            return requestJsonParamHandler.convert(json);
        }
        return Json.toObject(json, parameter.getGenericParameterType());
    }

    /**
     * 获取指定的参数名，如果{@link RequestJsonParam#value()}未指定，则使用{@link MethodParameter#getParameterName()}。
     */
    private String getParameterName(RequestJsonParam requestJsonParam, MethodParameter parameter) {
        return Assert.isEmpty(requestJsonParam.value()) ? parameter.getParameterName() : requestJsonParam.value().trim();
    }

    private Charset getContentTypeCharset(@Nullable MediaType contentType) {
        if (contentType != null && contentType.getCharset() != null) {
            return contentType.getCharset();
        } else {
            Charset charset = getDefaultCharset();
            org.springframework.util.Assert.state(charset != null, "No default charset");
            return charset;
        }
    }

    public Charset getDefaultCharset() {
        return this.defaultCharset;
    }

}
