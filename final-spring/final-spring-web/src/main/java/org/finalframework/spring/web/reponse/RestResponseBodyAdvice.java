package org.finalframework.spring.web.reponse;

import lombok.Setter;
import org.finalframework.json.Json;
import org.finalframework.spring.web.autoconfigure.ResponseBodyAdviceProperties;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * Wrap the return result of {@link org.springframework.web.method.HandlerMethod} which annotated by {@link ResponseBody} or {@link RestController}.
 *
 * @author likly
 * @version 1.0
 * @date 2018-09-29 09:45
 * @since 1.0
 */
@RestControllerAdvice
public class RestResponseBodyAdvice implements ResponseBodyAdvice<Object>, ApplicationContextAware, InitializingBean {

    private final ResponseBodyAdviceProperties responseBodyAdviceProperties;
    private ApplicationContext applicationContext;
    @Setter
    private ResponseBodyInterceptor<Object, ?> responseBodyInterceptor;

    public RestResponseBodyAdvice(ResponseBodyAdviceProperties responseBodyAdviceProperties) {
        this.responseBodyAdviceProperties = responseBodyAdviceProperties;
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
    public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        serverHttpResponse.getHeaders().setContentType(MediaType.APPLICATION_JSON_UTF8);
        final Object result = responseBodyInterceptor.intercept(body);
        if (body == null && methodParameter.getMethod() != null && methodParameter.getMethod().getReturnType() == String.class) {
            return Json.toJson(result);
        }
        if (body instanceof String) {
            return Json.toJson(result);
        }
        return result;
    }


    @Override
    public void afterPropertiesSet() throws Exception {


        this.responseBodyInterceptor = applicationContext.getBean(responseBodyAdviceProperties.getInterceptor());
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
