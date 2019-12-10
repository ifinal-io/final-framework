package org.finalframework.spring.web.converter;


import org.finalframework.core.converter.Converter;
import org.finalframework.json.jackson.view.JsonViewValue;
import org.finalframework.json.jackson.view.PageJsonViewValue;
import org.finalframework.data.result.R;
import org.finalframework.data.result.Result;

import java.util.List;

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
            JsonViewValue<? extends List<?>> jsonViewValue = pageJsonViewValue.getResult();
            Result<JsonViewValue<?>> result = R.success(jsonViewValue);
            result.setPage(pageJsonViewValue.toPageInfo());
            result.setView(jsonViewValue.getView());
            return result;
        }

        return R.success(body);
    }
}

