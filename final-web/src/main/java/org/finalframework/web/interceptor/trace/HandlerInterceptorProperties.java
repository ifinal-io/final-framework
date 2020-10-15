package org.finalframework.web.interceptor.trace;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;

/**
 * 基础的{@link org.springframework.web.servlet.HandlerInterceptor}配置，
 * 定义为接口是为了方便各实现更好的设置设置值
 *
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
