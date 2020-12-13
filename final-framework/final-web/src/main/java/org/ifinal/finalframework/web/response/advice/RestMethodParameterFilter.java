package org.ifinal.finalframework.web.response.advice;

import org.ifinal.finalframework.util.function.Filter;
import org.ifinal.finalframework.web.response.annotation.ResponseIgnore;
import org.ifinal.finalframework.web.response.annotation.RestResponseController;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest {@link MethodParameter} 方法过滤器。
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class RestMethodParameterFilter implements Filter<MethodParameter> {

    @Override
    public boolean matches(final MethodParameter methodParameter) {

        if (methodParameter.hasMethodAnnotation(ResponseIgnore.class) || methodParameter.getDeclaringClass().isAnnotationPresent(ResponseIgnore.class)) {
            return false;
        }

        if (methodParameter.getDeclaringClass().isAnnotationPresent(RestResponseController.class)) {
            return true;
        }

        return methodParameter.hasMethodAnnotation(ResponseBody.class) || methodParameter.getDeclaringClass().isAnnotationPresent(RestController.class);
    }

}
