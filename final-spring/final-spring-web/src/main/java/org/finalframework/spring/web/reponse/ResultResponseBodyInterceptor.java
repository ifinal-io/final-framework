package org.finalframework.spring.web.reponse;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import org.finalframework.data.result.R;
import org.finalframework.data.result.Result;
import org.slf4j.MDC;
import org.springframework.lang.NonNull;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-24 15:08:05
 * @since 1.0
 */
public class ResultResponseBodyInterceptor implements ResponseBodyInterceptor<Object, Result> {

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
            final PageInfo pageInfo = ((Page) body).toPageInfo();
            org.finalframework.data.result.Page pageResult = org.finalframework.data.result.Page.builder()
                    .page(pageInfo.getPageNum())
                    .size(pageInfo.getPageSize())
                    .pages(pageInfo.getPages())
                    .total(pageInfo.getTotal())
                    .result(pageInfo.getList())
                    .firstPage(pageInfo.isIsFirstPage())
                    .lastPage(pageInfo.isIsLastPage())
                    .build();
            return R.success(pageResult);
        } else if (body instanceof Result) {
            return (Result<?>) body;
        }

        return R.success(body);
    }

}
