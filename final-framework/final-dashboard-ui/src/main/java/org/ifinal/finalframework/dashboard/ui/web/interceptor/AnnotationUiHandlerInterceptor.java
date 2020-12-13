package org.ifinal.finalframework.dashboard.ui.web.interceptor;

import java.lang.annotation.Annotation;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.ifinal.finalframework.dashboard.ui.model.Page;
import org.ifinal.finalframework.util.Asserts;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public abstract class AnnotationUiHandlerInterceptor<A extends Annotation> implements UIHandlerInterceptor {

    private final Class<A> ann;

    protected AnnotationUiHandlerInterceptor(final Class<A> ann) {

        this.ann = ann;
    }

    @Override
    public final void postHandle(final HttpServletRequest request, final HttpServletResponse response,
        final HandlerMethod handler, final Page page, final ModelAndView modelAndView) {

        Set<A> annotations = AnnotatedElementUtils.findAllMergedAnnotations(handler.getMethod(), ann);
        if (Asserts.isEmpty(annotations)) {
            return;
        }
        postHandle(request, response, handler, page, annotations, modelAndView);
    }

    protected abstract void postHandle(final HttpServletRequest request, final HttpServletResponse response,
        final HandlerMethod handler, final Page page,
        final Set<A> anns, final ModelAndView modelAndView);

}

