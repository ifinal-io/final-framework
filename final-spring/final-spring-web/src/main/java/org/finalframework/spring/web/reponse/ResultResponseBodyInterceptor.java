package org.finalframework.spring.web.reponse;


import com.github.pagehelper.Page;
import org.finalframework.data.result.R;
import org.finalframework.data.result.Result;
import org.finalframework.spring.web.converter.Page2PageResultConverter;
import org.slf4j.MDC;
import org.springframework.lang.NonNull;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-24 15:08:05
 * @since 1.0
 */
public class ResultResponseBodyInterceptor implements ResponseBodyInterceptor<Object, Result> {
    private final Page2PageResultConverter page2PageResultConverter = new Page2PageResultConverter();

    @Override
    public Result intercept(Object body) {
        return this.build(body);
    }

    private Result build(Object body) {
        final Result result = buildResult(body);
        String trace = MDC.get("trace");
        result.setTrace(trace);
        return result;
    }

    @NonNull
    private Result buildResult(Object body) {
        if (body == null) return R.success();

        if (body instanceof Page) {
            return R.success(page2PageResultConverter.convert((Page) body));
        } else if (body instanceof Result) {
            return (Result<?>) body;
        }

        return R.success(body);
    }

}
