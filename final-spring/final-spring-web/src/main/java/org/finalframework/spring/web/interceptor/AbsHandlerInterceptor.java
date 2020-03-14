package org.finalframework.spring.web.interceptor;


import org.finalframework.spring.annotation.factory.SpringHandlerInterceptor;

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


    private final List<String> pathPatterns = new ArrayList<>();
    private final List<String> excludePathPatterns = new ArrayList<>();


    public AbsHandlerInterceptor() {
        SpringHandlerInterceptor annotation = this.getClass().getAnnotation(SpringHandlerInterceptor.class);
        if (annotation != null) {
            setPathPatterns(Arrays.asList(annotation.includes()));
            setExcludePathPatterns(Arrays.asList(annotation.excludes()));
        }
    }

    @Override
    public List<String> getPathPatterns() {
        return this.pathPatterns;
    }

    @Override
    public void setPathPatterns(List<String> patterns) {
        this.pathPatterns.clear();
        this.pathPatterns.addAll(patterns);
    }

    @Override
    public List<String> getExcludePathPatterns() {
        return this.excludePathPatterns;
    }

    @Override
    public void setExcludePathPatterns(List<String> patterns) {
        this.excludePathPatterns.clear();
        this.excludePathPatterns.addAll(patterns);
    }
}

