package cn.com.likly.finalframework.spring.web.exception;

import cn.com.likly.finalframework.spring.annotation.RestExceptionHandler;
import cn.com.likly.finalframework.spring.util.BeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-29 15:49
 * @since 1.0
 */
@Slf4j
@RestControllerAdvice
public class DefaultRestExceptionHandler implements ApplicationContextAware {

    private ApplicationContext applicationContext;
    private List<ExceptionHandlerBean> exceptionHandlerBeans;

    @PostConstruct
    @SuppressWarnings("unchecked")
    public void init() {
        this.exceptionHandlerBeans = BeanUtils.findAllBeansAnnotatedBy(applicationContext, RestExceptionHandler.class)
                .map(it -> {

                    if (!(it instanceof cn.com.likly.finalframework.spring.web.exception.ExceptionHandler)) {
                        throw new IllegalStateException("the exception handler must implements ExceptionHandler!");
                    }

                    Order order = it.getClass().getAnnotation(Order.class);
                    return new ExceptionHandlerBean(order == null ? 0 : order.value(), (cn.com.likly.finalframework.spring.web.exception.ExceptionHandler<Throwable, Object>) it);
                })
                .sorted()
                .collect(Collectors.toList());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object handlerException(Exception e) throws Throwable {
        for (ExceptionHandlerBean item : exceptionHandlerBeans) {
            if (item.isSupported(e)) {
                return item.handle(e);
            }
        }
        logger.error("UnCatchException", e);
        throw e;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
