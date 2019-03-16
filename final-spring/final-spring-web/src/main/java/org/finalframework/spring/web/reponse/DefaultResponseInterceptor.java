package org.finalframework.spring.web.reponse;

/**
 * @author likly
 * @version 1.0
 * @date 2019-02-14 19:30:21
 * @since 1.0
 */
public class DefaultResponseInterceptor implements ResponseInterceptor {
    @Override
    public Object intercept(Object o) {
        return o;
    }
}
