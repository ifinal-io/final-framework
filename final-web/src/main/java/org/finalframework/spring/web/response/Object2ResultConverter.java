

package org.finalframework.spring.web.response;


import org.finalframework.annotation.result.R;
import org.finalframework.annotation.result.Result;
import org.finalframework.core.converter.Converter;

/**
 * @author likly
 * @version 1.0
 * @date 2019-12-09 11:17:03
 * @since 1.0
 */
public class Object2ResultConverter implements Converter<Object, Result<?>> {
    @Override
    public Result<?> convert(Object body) {
        if (body == null) {
            return R.success();
        }

        if (body instanceof Result) {
            return (Result<?>) body;
        }


        return R.success(body);
    }
}

