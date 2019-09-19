package org.finalframework.spring.web.reponse;

/**
 * @author likly
 * @version 1.0
 * @date 2019-09-06 18:20:06
 * @see ResponseBodyJacksonSerializer
 * @since 1.0
 */
public interface ResponseBodySerializer<T> {

    Class<? extends T> type();

}
