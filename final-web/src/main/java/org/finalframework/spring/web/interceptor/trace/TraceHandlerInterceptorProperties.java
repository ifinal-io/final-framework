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

package org.finalframework.spring.web.interceptor.trace;


import org.finalframework.core.generator.TraceGenerator;
import org.finalframework.core.generator.UUIDTraceGenerator;
import org.finalframework.spring.web.interceptor.HandlerInterceptorProperties;
import org.slf4j.MDC;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.Ordered;

import java.util.Collections;
import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2020-03-14 10:13:51
 * @since 1.0
 */
@ConfigurationProperties(prefix = "final.trace")
public class TraceHandlerInterceptorProperties implements HandlerInterceptorProperties {
    private static final String TRACE = "trace";
    /**
     * TRACE 名称，放置到{@link MDC}中的KEY的名称
     */
    private String traceName = TRACE;
    /**
     * TRACE 参数名称
     */
    private String paramName = TRACE;
    /**
     * TRACE 请求头名称
     */
    private String headerName = TRACE;

    private Class<? extends TraceGenerator> generator = UUIDTraceGenerator.class;

    /**
     * 排序
     */
    private Integer order = Ordered.HIGHEST_PRECEDENCE;

    /**
     * 包含的路径规则
     */
    private List<String> pathPatterns = Collections.singletonList("/**");
    /**
     * 排除的路径规则
     */
    private List<String> excludePathPatterns;

    public String getTraceName() {
        return traceName;
    }

    public void setTraceName(String traceName) {
        this.traceName = traceName;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getHeaderName() {
        return headerName;
    }

    public void setHeaderName(String headerName) {
        this.headerName = headerName;
    }

    public Class<? extends TraceGenerator> getGenerator() {
        return generator;
    }

    public void setGenerator(Class<? extends TraceGenerator> generator) {
        this.generator = generator;
    }

    @Override
    public Integer getOrder() {
        return order;
    }

    @Override
    public void setOrder(Integer order) {
        this.order = order;
    }

    @Override
    public List<String> getPathPatterns() {
        return pathPatterns;
    }

    @Override
    public void setPathPatterns(List<String> pathPatterns) {
        this.pathPatterns = pathPatterns;
    }

    @Override
    public List<String> getExcludePathPatterns() {
        return excludePathPatterns;
    }

    @Override
    public void setExcludePathPatterns(List<String> excludePathPatterns) {
        this.excludePathPatterns = excludePathPatterns;
    }
}

