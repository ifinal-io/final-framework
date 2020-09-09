

package org.finalframework.spring.web.interceptor;

import org.springframework.web.servlet.AsyncHandlerInterceptor;

import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2020-03-14 09:26:20
 * @since 1.0
 */
public interface IHandlerInterceptor extends AsyncHandlerInterceptor {

    List<String> getPathPatterns();

    void setPathPatterns(List<String> patterns);

    List<String> getExcludePathPatterns();

    void setExcludePathPatterns(List<String> patterns);

    Integer getOrder();

    void setOrder(Integer order);


}
