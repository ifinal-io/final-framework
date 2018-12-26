package cn.com.likly.finalframework.spring.web.reponse;

import cn.com.likly.finalframework.data.result.R;
import cn.com.likly.finalframework.data.result.Result;
import cn.com.likly.finalframework.json.Json;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import org.slf4j.MDC;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Wrap the return result of {@link org.springframework.web.method.HandlerMethod} which annotated by {@link ResponseBody} or {@link RestController}.
 *
 * @author likly
 * @version 1.0
 * @date 2018-09-29 09:45
 * @since 1.0
 */
@RestControllerAdvice
public class ResultResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    private static final Map<Integer, HttpStatus> HTTP_STATUS_MAP = new LinkedHashMap<>();

    static {
        HTTP_STATUS_MAP.put(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST);
        HTTP_STATUS_MAP.put(HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN);
        HTTP_STATUS_MAP.put(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED);
    }

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
        setHttpResponseStatus(serverHttpResponse, result);

        if (object instanceof String) {
            return Json.toJson(result);
        }


        return result;
    }

    /**
     * set {@link ServerHttpResponse#setStatusCode(HttpStatus)}  when the {@link Result#status} is a special http status
     */
    private void setHttpResponseStatus(ServerHttpResponse response, Result result) {
        if (HTTP_STATUS_MAP.containsKey(result.getStatus())) {
            response.setStatusCode(HTTP_STATUS_MAP.get(result.getStatus()));
        }
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
            cn.com.likly.finalframework.data.result.Page pageResult = cn.com.likly.finalframework.data.result.Page.builder()
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
