package com.ilikly.finalframework.spring.web.reponse;

/**
 * @author likly
 * @version 1.0
 * @date 2019-02-24 10:02:30
 * @since 1.0
 */
public interface ResponseBodyHandler<BODY, RESULT> {
    RESULT handle(BODY body);
}
