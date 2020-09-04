/*
 * Copyright (c) 2018-2020.  the original author or authors.
 *  <p>
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  <p>
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  <p>
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package org.finalframework.ui.web.interceptor;


import org.finalframework.ui.model.Page;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author likly
 * @version 1.0
 * @date 2019-11-09 13:57:51
 * @since 1.0
 */
public interface UIHandlerInterceptor extends HandlerInterceptor {
    @Override
    default void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
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

    void postHandle(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler, Page page, ModelAndView modelAndView);

}

