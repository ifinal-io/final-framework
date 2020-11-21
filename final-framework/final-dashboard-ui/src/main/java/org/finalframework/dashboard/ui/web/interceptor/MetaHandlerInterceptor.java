package org.finalframework.dashboard.ui.web.interceptor;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.finalframework.dashboard.ui.annotation.Meta;
import org.finalframework.dashboard.ui.model.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author likly
 * @version 1.0
 * @date 2020/11/21 17:15:52
 * @since 1.0
 */
@Component
public class MetaHandlerInterceptor extends AnnotationUiHandlerInterceptor<Meta> {


    protected MetaHandlerInterceptor() {
        super(Meta.class);
    }

    @Override
    protected void postHandle(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler, Page page, Set<Meta> anns, ModelAndView modelAndView) {
        modelAndView.getModelMap().put("metas", anns.stream()
                .map(item -> new MetaInfo(item.name(), item.content()))
                .collect(Collectors.toList()));
    }

    @Data
    @AllArgsConstructor
    static class MetaInfo implements Serializable {
        private String name;
        private String content;
    }
}
