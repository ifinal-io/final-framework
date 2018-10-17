package cn.com.likly.finalframework.spring.web.api.reponse;

import cn.com.likly.finalframework.data.result.R;
import cn.com.likly.finalframework.data.result.Result;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import org.slf4j.MDC;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-29 09:45
 * @since 1.0
 */
@RestControllerAdvice
public class ResultResponseBodyAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return methodParameter.hasMethodAnnotation(ResponseBody.class) ||
                (methodParameter.getMethod() != null && methodParameter.getMethod().getDeclaringClass().getAnnotation(RestController.class) != null);
    }

    @Override
    public Object beforeBodyWrite(Object object, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {

        serverHttpResponse.getHeaders().setContentType(MediaType.APPLICATION_JSON_UTF8);
        String trace = MDC.get("trace");
        Result<?> result = buildResult(object);
        result.setTrace(trace);
        return result;
    }

    @SuppressWarnings("unchecked")
    private Result<?> buildResult(Object object) {
        if (object == null) return R.success();

        if (object instanceof Page) {
            final PageInfo pageInfo = ((Page) object).toPageInfo();
            cn.com.likly.finalframework.data.result.Page pageResult = new cn.com.likly.finalframework.data.result.Page(
                    pageInfo.getPageNum(),
                    pageInfo.getPageSize(),
                    pageInfo.getPages(),
                    pageInfo.getTotal(),
                    pageInfo.getList(),
                    pageInfo.isIsFirstPage(),
                    pageInfo.isIsLastPage()
            );

            return R.success(pageResult);
        } else if (object instanceof Result) {
            return (Result<?>) object;
        }

        return R.success(object);

    }


}
