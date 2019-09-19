package org.finalframework.spring.web.reponse;

import com.fasterxml.jackson.databind.JsonSerializer;

/**
 * @author likly
 * @version 1.0
 * @date 2019-09-14 18:34:21
 * @since 1.0
 */
public abstract class ResponseBodyJacksonSerializer<T> extends JsonSerializer<T> implements ResponseBodySerializer<T> {
}
