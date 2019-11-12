package org.finalframework.ui.web.interceptor;


import org.finalframework.spring.annotation.factory.SpringHandlerInterceptor;
import org.finalframework.ui.annotation.Title;
import org.finalframework.ui.model.Page;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author likly
 * @version 1.0
 * @date 2019-11-09 14:20:32
 * @since 1.0
 */
@SpringHandlerInterceptor
public class TitleHandlerInterceptor extends AnnotationUiHandlerInterceptor<Title> {

    public TitleHandlerInterceptor() {
        super(Title.class);
    }

    @Override
    protected void postHandle(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler, Page page, Title ann, ModelAndView modelAndView) {
        page.setTitle(ann.value());
    }
}

