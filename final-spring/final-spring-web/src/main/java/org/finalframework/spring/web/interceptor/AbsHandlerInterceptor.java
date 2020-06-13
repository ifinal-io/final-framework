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

package org.finalframework.spring.web.interceptor;


import org.finalframework.core.Assert;
import org.finalframework.spring.annotation.factory.SpringHandlerInterceptor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2020-03-14 09:29:43
 * @since 1.0
 */
public class AbsHandlerInterceptor implements IHandlerInterceptor {

    private Integer order = Ordered.LOWEST_PRECEDENCE;
    private final List<String> pathPatterns = new ArrayList<>();
    private final List<String> excludePathPatterns = new ArrayList<>();


    public AbsHandlerInterceptor() {
        this(null);
    }

    public AbsHandlerInterceptor(HandlerInterceptorProperties properties) {
        if (properties != null) {
            this.setOrder(properties.getOrder());
            this.setPathPatterns(properties.getPathPatterns());
            this.setExcludePathPatterns(properties.getExcludePathPatterns());
        } else {
            init();
        }
    }

    private void init() {
        SpringHandlerInterceptor annotation = this.getClass().getAnnotation(SpringHandlerInterceptor.class);
        if (annotation != null) {
            setPathPatterns(Arrays.asList(annotation.includes()));
            setExcludePathPatterns(Arrays.asList(annotation.excludes()));
        }
        Order order = this.getClass().getAnnotation(Order.class);
        if (order != null) {
            this.setOrder(order.value());
        }
    }

    @Override
    public List<String> getPathPatterns() {
        return this.pathPatterns;
    }

    @Override
    public void setPathPatterns(List<String> patterns) {
        this.pathPatterns.clear();
        if (Assert.nonEmpty(patterns)) {
            this.pathPatterns.addAll(patterns);
        }
    }

    @Override
    public List<String> getExcludePathPatterns() {
        return this.excludePathPatterns;
    }

    @Override
    public void setExcludePathPatterns(List<String> patterns) {
        this.excludePathPatterns.clear();
        if (Assert.nonEmpty(patterns)) {
            this.excludePathPatterns.addAll(patterns);
        }
    }

    @Override
    public Integer getOrder() {
        return order;
    }

    @Override
    public void setOrder(Integer order) {
        this.order = order;
    }
}

