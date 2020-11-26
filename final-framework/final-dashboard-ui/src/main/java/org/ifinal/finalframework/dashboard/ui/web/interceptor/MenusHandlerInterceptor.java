package org.ifinal.finalframework.dashboard.ui.web.interceptor;


import org.ifinal.finalframework.dashboard.ui.annotation.Menus;
import org.ifinal.finalframework.dashboard.ui.model.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Set;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
public class MenusHandlerInterceptor extends AnnotationUiHandlerInterceptor<Menus> {

    protected MenusHandlerInterceptor() {
        super(Menus.class);
    }

    @Override
    protected void postHandle(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler, Page page, Set<Menus> anns, ModelAndView modelAndView) {
        anns.stream().findFirst().ifPresent(menus -> page.setMenus(Arrays.asList(menus.value())));
    }

}

