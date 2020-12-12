package org.ifinal.finalframework.dashboard.ui.web.interceptor;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.ifinal.finalframework.dashboard.ui.annotation.Meta;
import org.ifinal.finalframework.dashboard.ui.model.Page;
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
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
public class MetaHandlerInterceptor extends AnnotationUiHandlerInterceptor<Meta> {


    protected MetaHandlerInterceptor() {
        super(Meta.class);
    }

    @Override
    protected void postHandle(final HttpServletRequest request, final HttpServletResponse response, final HandlerMethod handler, final Page page, final Set<Meta> anns, final ModelAndView modelAndView) {

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
