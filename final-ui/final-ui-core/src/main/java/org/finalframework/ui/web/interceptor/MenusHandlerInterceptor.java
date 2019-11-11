package org.finalframework.ui.web.interceptor;


import org.finalframework.spring.web.interceptor.annotation.HandlerInterceptor;
import org.finalframework.ui.annotation.Menus;
import org.finalframework.ui.model.Page;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
 * @author likly
 * @version 1.0
 * @date 2019-11-09 13:57:51
 * @since 1.0
 */
@HandlerInterceptor
public class MenusHandlerInterceptor extends AnnotationUiHandlerInterceptor<Menus> {

    protected MenusHandlerInterceptor() {
        super(Menus.class);
    }

    @Override
    protected void postHandle(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler, Page page, Menus ann, ModelAndView modelAndView) {
        page.setMenus(Arrays.asList(ann.value()));
    }
}

