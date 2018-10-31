package cn.com.likly.finalframework.spring.web.reponse;

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
 * Wrap the return result of {@link org.springframework.web.method.HandlerMethod} which annotated by {@link ResponseBody} or {@link RestController}.
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

    /**
     * build {@link Result} of body
     *
     * @param body result return by handler method or exception handler
     */
    @SuppressWarnings("unchecked")
    private Result<?> buildResult(Object body) {
        if (body == null) return R.success();

        if (body instanceof Page) {
            final PageInfo pageInfo = ((Page) body).toPageInfo();
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
        } else if (body instanceof Result) {
            return (Result<?>) body;
        }

        return R.success(body);

    }


}
