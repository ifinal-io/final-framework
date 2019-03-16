package org.finalframework.spring.web.reponse;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import lombok.Setter;
import org.finalframework.data.result.R;
import org.finalframework.data.result.Result;
import org.finalframework.json.Json;
import org.finalframework.spring.web.autoconfigure.ResponseBodyAdviceProperties;
import org.slf4j.MDC;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
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

/**
 * Wrap the return result of {@link org.springframework.web.method.HandlerMethod} which annotated by {@link ResponseBody} or {@link RestController}.
 *
 * @author likly
 * @version 1.0
 * @date 2018-09-29 09:45
 * @since 1.0
 */
@RestControllerAdvice
public class ResultResponseBodyAdvice implements ResponseBodyAdvice<Object>, ApplicationContextAware, InitializingBean {

    private final ResponseBodyAdviceProperties responseBodyAdviceProperties;
    private ApplicationContext applicationContext;
    @Setter
    private ResponseInterceptor responseInterceptor;

    public ResultResponseBodyAdvice(ResponseBodyAdviceProperties responseBodyAdviceProperties) {
        this.responseBodyAdviceProperties = responseBodyAdviceProperties;
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {

        if (methodParameter.hasMethodAnnotation(ResponseIgnore.class) ||
                (methodParameter.getMethod() != null && methodParameter.getMethod().getDeclaringClass().getAnnotation(ResponseIgnore.class) != null)) {
            return false;
        }

        if (methodParameter.getMethod() != null && methodParameter.getMethod().getDeclaringClass().getAnnotation(RestResponseController.class) != null) {
            return true;
        }

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
        Object value = responseInterceptor == null ? result : responseInterceptor.intercept(result);
        if (object instanceof String) {
            return Json.toJson(value);
        }
        return value;
    }

    /**
     * set {@link ServerHttpResponse#setStatusCode(HttpStatus)}  when the {@link Result#getStatus()} is a special http status
     */
    private void setHttpResponseStatus(ServerHttpResponse response, Result result) {
        if (responseBodyAdviceProperties.isSyncStatus()) {
            HttpStatus httpStatus = HttpStatus.resolve(result.getStatus());
            if (httpStatus != null) {
                response.setStatusCode(httpStatus);
            }
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


    @Override
    public void afterPropertiesSet() throws Exception {
        this.responseInterceptor = applicationContext.getBean(responseBodyAdviceProperties.getInterceptor());
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
