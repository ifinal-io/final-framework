package org.finalframework.dashboard.ui.web.interceptor;


import org.finalframework.dashboard.ui.annotation.Title;
import org.finalframework.dashboard.ui.model.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

/**
 * @author likly
 * @version 1.0
 * @date 2019-11-09 14:20:32
 * @since 1.0
 */
@Component
public class TitleHandlerInterceptor extends AnnotationUiHandlerInterceptor<Title> {

    public TitleHandlerInterceptor() {
        super(Title.class);
    }

    @Override
    protected void postHandle(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler, Page page, Set<Title> anns, ModelAndView modelAndView) {
        anns.stream().findFirst().ifPresent(title -> page.setTitle(title.value()));
    }

}

