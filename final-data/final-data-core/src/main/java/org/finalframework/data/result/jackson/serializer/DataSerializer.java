package org.finalframework.data.result.jackson.serializer;

import com.fasterxml.jackson.databind.JsonSerializer;

/**
 * @author likly
 * @version 1.0
 * @date 2019-08-26 18:02:48
 * @since 1.0
 */
public abstract class DataSerializer<T> extends JsonSerializer<T> {

    abstract boolean supports(Object value);

}
