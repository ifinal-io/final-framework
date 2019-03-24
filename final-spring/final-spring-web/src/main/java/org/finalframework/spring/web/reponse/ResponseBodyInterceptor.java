package org.finalframework.spring.web.reponse;

/**
 * @author likly
 * @version 1.0
 * @date 2019-02-14 19:27:05
 * @since 1.0
 */
public interface ResponseBodyInterceptor<BODY, RESULT> {

    RESULT intercept(BODY body);
}
