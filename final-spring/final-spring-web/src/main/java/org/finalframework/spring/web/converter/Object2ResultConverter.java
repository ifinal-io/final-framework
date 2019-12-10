package org.finalframework.spring.web.converter;


import org.finalframework.core.converter.Converter;
import org.finalframework.json.jackson.view.JsonViewValue;
import org.finalframework.json.jackson.view.PageJsonViewValue;
import org.finalframework.data.result.R;
import org.finalframework.data.result.Result;

/**
 * @author likly
 * @version 1.0
 * @date 2019-12-09 11:17:03
 * @since 1.0
 */
public class Object2ResultConverter implements Converter<Object, Result<?>> {
    @Override
    public Result<?> convert(Object body) {
        if (body == null) return R.success();

        if (body instanceof Result) {
            return (Result<?>) body;
        }

        if (body instanceof PageJsonViewValue) {
            PageJsonViewValue<?> pageJsonViewValue = (PageJsonViewValue<?>) body;
            Result<JsonViewValue<?>> result = R.success(pageJsonViewValue.getValue());
            result.setPage(pageJsonViewValue.getPageInfo());
            result.setView(pageJsonViewValue.getView());
            return result;
        }

        return R.success(body);
    }
}

