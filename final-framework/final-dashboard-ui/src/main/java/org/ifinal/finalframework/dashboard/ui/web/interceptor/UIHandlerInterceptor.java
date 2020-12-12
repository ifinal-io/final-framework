package org.ifinal.finalframework.dashboard.ui.web.interceptor;


import org.ifinal.finalframework.dashboard.ui.model.Page;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface UIHandlerInterceptor extends HandlerInterceptor {
    @Override
    default void postHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler, final ModelAndView modelAndView) throws Exception {

        if (handler instanceof HandlerMethod && modelAndView != null) {
            Page page;
            if (modelAndView.getModel().containsKey("page")) {
                page = (Page) modelAndView.getModel().get("page");
            } else {
                page = new Page();
                modelAndView.getModel().put("page", page);
            }
            if (page == null) {
                page = new Page();
                modelAndView.getModel().put("page", page);
            }
            postHandle(request, response, (HandlerMethod) handler, page, modelAndView);

        }
    }

    void postHandle(final HttpServletRequest request, final HttpServletResponse response, final HandlerMethod handler, final Page page, final ModelAndView modelAndView);

}

