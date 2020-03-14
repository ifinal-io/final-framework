package org.finalframework.spring.web.interceptor;

import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2020-03-14 10:14:15
 * @since 1.0
 */
public interface HandlerInterceptorProperties {

    Integer getOrder();

    void setOrder(Integer order);

    List<String> getPathPatterns();

    void setPathPatterns(List<String> patterns);

    List<String> getExcludePathPatterns();

    void setExcludePathPatterns(List<String> patterns);
}
