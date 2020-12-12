package org.ifinal.finalframework.web.response;


import org.ifinal.finalframework.annotation.core.result.R;
import org.ifinal.finalframework.annotation.core.result.Result;
import org.ifinal.finalframework.util.function.Converter;

import java.io.Serializable;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class Object2ResultConverter implements Converter<Object, Result<?>> {
    @Override
    public Result<?> convert(final Object body) {

        if (body == null) {
            return R.success();
        }

        if (body instanceof Result) {
            return (Result<?>) body;
        }

        if (body instanceof Serializable) {
            return R.success((Serializable) body);
        }

        throw new IllegalArgumentException(body.getClass() + " must impl Serializable");

    }
}

