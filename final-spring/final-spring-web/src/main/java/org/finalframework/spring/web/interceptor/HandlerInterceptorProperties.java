package org.finalframework.spring.web.interceptor;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2020-03-14 10:14:15
 * @since 1.0
 */
public interface HandlerInterceptorProperties {

    @NonNull
    Integer getOrder();

    void setOrder(Integer order);

    @Nullable
    List<String> getPathPatterns();

    void setPathPatterns(List<String> patterns);

    @Nullable
    List<String> getExcludePathPatterns();

    void setExcludePathPatterns(List<String> patterns);
}
