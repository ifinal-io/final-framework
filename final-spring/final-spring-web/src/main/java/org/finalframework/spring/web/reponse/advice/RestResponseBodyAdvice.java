package org.finalframework.spring.web.reponse.advice;

import org.finalframework.spring.web.reponse.ResponseIgnore;
import org.finalframework.spring.web.reponse.RestResponseController;
import org.springframework.core.MethodParameter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author likly
 * @version 1.0
 * @date 2019-09-24 23:22:32
 * @see EnumsResponseBodyAdvice
 * @see PageResponseBodyAdvice
 * @see ResultResponseBodyAdvice
 * @see ResponsibleResponseBodyAdvice
 * @since 1.0
 */
public interface RestResponseBodyAdvice<T> extends ResponseBodyAdvice<T> {
    /**
     * @param methodParameter
     * @param converterType
     * @return
     */
    @Override
    public default boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> converterType) {
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

}
