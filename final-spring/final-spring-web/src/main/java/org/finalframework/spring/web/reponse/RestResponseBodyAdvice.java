package org.finalframework.spring.web.reponse;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Setter;
import org.finalframework.data.result.Result;
import org.finalframework.json.Json;
import org.finalframework.spring.web.autoconfigure.ResponseBodyAdviceProperties;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * Wrap the return result of {@link HandlerMethod} which annotated by {@link ResponseBody} or {@link RestController}.
 *
 * @author likly
 * @version 1.0
 * @date 2018-09-29 09:45
 * @since 1.0
 */
@RestControllerAdvice
public class RestResponseBodyAdvice implements ResponseBodyAdvice<Object>, ApplicationContextAware, InitializingBean {

    private ApplicationContext applicationContext;

    private final ResponseBodyAdviceProperties properties;
    @Setter
    private ResponseBodyInterceptor<Object, ?> defaultResponseBodyInterceptor = new ResultResponseBodyInterceptor();
    @Setter
    private boolean syncStatus;
    @Setter
    private ResponseBodyInterceptor<Object, ?> responseBodyInterceptor;

    public RestResponseBodyAdvice(ResponseBodyAdviceProperties properties) {
        this.properties = properties;
        this.syncStatus = properties.isSyncStatus();
    }


    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {

        if (methodParameter.hasMethodAnnotation(ResponseIgnore.class) ||
                (methodParameter.getMethod() != null && methodParameter.getMethod().getDeclaringClass().getAnnotation(ResponseIgnore.class) != null)) {
            return false;
        }

        if (methodParameter.getMethod() != null && methodParameter.getMethod().getDeclaringClass().getAnnotation(RestResponseController.class) != null) {
            return true;
        }

        return methodParameter.hasMethodAnnotation(ResponseBody.class) ||
                (methodParameter.getMethod() != null && methodParameter.getMethod().getDeclaringClass().getAnnotation(RestController.class) != null);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest request, ServerHttpResponse response) {
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON_UTF8);
        final Object defaultResult = defaultResponseBodyInterceptor == null ? body : defaultResponseBodyInterceptor.intercept(body);


        if (syncStatus && defaultResult instanceof Result) {
            final HttpStatus httpStatus = HttpStatus.resolve(((Result) defaultResult).getStatus());
            if (httpStatus != null) {
                response.setStatusCode(httpStatus);
            }
        }

        final Object result = responseBodyInterceptor == null ? defaultResult : responseBodyInterceptor.intercept(defaultResult);

        if (result instanceof Result) {
            JsonView jsonView = methodParameter.getMethodAnnotation(JsonView.class);

            if (jsonView != null) {
                Class<?>[] views = jsonView.value();

                if (views.length > 1) {
                    throw new IllegalArgumentException("Controller @JsonView only support one view.");
                } else {
                    ((Result) result).setView(views[0]);
                }
            }

        }


        if (body == null && methodParameter.getMethod() != null && methodParameter.getMethod().getReturnType() == String.class) {
            return Json.toJson(result);
        }
        if (body instanceof String) {
            return Json.toJson(result);
        }
        return result;
    }


    @Override
    @SuppressWarnings("unchecked")
    public void afterPropertiesSet() throws Exception {

        if (properties.getDefaultInterceptor() != null) {
            this.defaultResponseBodyInterceptor = applicationContext.getBean(properties.getDefaultInterceptor());
        }

        if (properties.getInterceptor() != null) {
            this.responseBodyInterceptor = applicationContext.getBean(properties.getInterceptor());
        }


    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
