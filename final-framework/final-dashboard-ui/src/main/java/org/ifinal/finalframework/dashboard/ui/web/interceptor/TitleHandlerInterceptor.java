package org.ifinal.finalframework.dashboard.ui.web.interceptor;

import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.ifinal.finalframework.dashboard.ui.annotation.Title;
import org.ifinal.finalframework.dashboard.ui.model.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
public class TitleHandlerInterceptor extends AnnotationUiHandlerInterceptor<Title> {

    public TitleHandlerInterceptor() {
        super(Title.class);
    }

    @Override
    protected void postHandle(final HttpServletRequest request, final HttpServletResponse response,
        final HandlerMethod handler, final Page page,
        final Set<Title> anns, final ModelAndView modelAndView) {

        anns.stream().findFirst().ifPresent(title -> page.setTitle(title.value()));
    }

}

