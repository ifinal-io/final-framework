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

