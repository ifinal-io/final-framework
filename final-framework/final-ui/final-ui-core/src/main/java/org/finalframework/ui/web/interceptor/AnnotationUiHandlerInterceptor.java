package org.finalframework.ui.web.interceptor;


import org.finalframework.ui.model.Page;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;

/**
 * @author likly
 * @version 1.0
 * @date 2019-11-09 14:21:41
 * @since 1.0
 */
public abstract class AnnotationUiHandlerInterceptor<A extends Annotation> implements UIHandlerInterceptor {
    private final Class<? extends Annotation> ann;

    protected AnnotationUiHandlerInterceptor(Class<? extends Annotation> ann) {
        this.ann = ann;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler, Page page, ModelAndView modelAndView) {
        if (!handler.hasMethodAnnotation(ann)) return;
        postHandle(request, response, handler, page, (A) handler.getMethodAnnotation(ann), modelAndView);
    }

    protected abstract void postHandle(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler, Page page, A ann, ModelAndView modelAndView);
}

