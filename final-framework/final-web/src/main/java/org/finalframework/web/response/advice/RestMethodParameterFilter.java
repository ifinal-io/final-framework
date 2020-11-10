package org.finalframework.web.response.advice;

import org.finalframework.util.function.Filter;
import org.finalframework.web.response.annotation.ResponseIgnore;
import org.finalframework.web.response.annotation.RestResponseController;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest {@link MethodParameter} 方法过滤器。
 *
 * @author likly
 * @version 1.0
 * @date 2019-09-27 10:53:56
 * @since 1.0
 */
public class RestMethodParameterFilter implements Filter<MethodParameter> {

    @Override
    public boolean matches(MethodParameter methodParameter) {
        if (methodParameter.hasMethodAnnotation(ResponseIgnore.class) || methodParameter.getDeclaringClass().isAnnotationPresent(ResponseIgnore.class)) {
            return false;
        }

        if (methodParameter.getDeclaringClass().isAnnotationPresent(RestResponseController.class)) {
            return true;
        }

        return methodParameter.hasMethodAnnotation(ResponseBody.class) || methodParameter.getDeclaringClass().isAnnotationPresent(RestController.class);
    }
}
