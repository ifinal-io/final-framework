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

