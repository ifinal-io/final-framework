package org.ifinal.finalframework.dashboard.ui.web.interceptor;


import org.ifinal.finalframework.dashboard.ui.model.Page;
import org.ifinal.finalframework.util.Asserts;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.util.Set;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public abstract class AnnotationUiHandlerInterceptor<A extends Annotation> implements UIHandlerInterceptor {
    private final Class<A> ann;

    protected AnnotationUiHandlerInterceptor(Class<A> ann) {
        this.ann = ann;
    }

    @Override
    public final void postHandle(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler, Page page, ModelAndView modelAndView) {
        Set<A> annotations = AnnotatedElementUtils.findAllMergedAnnotations(handler.getMethod(), ann);
        if (Asserts.isEmpty(annotations)) return;
        postHandle(request, response, handler, page, annotations, modelAndView);
    }

    protected abstract void postHandle(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler, Page page, Set<A> anns, ModelAndView modelAndView);
}

