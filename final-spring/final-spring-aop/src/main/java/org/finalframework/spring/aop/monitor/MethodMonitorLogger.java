package org.finalframework.spring.aop.monitor;

import lombok.extern.slf4j.Slf4j;
import org.finalframework.data.exception.IException;
import org.finalframework.json.Json;

import javax.annotation.PostConstruct;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-09 13:15
 * @since 1.0
 */
@Slf4j
public class MethodMonitorLogger implements MethodMonitorListener {

    @PostConstruct
    public void init() {
        MethodMonitorRegistry.getInstance().register(this);
    }

    @Override
    public void onFinish(MethodPoint point, Object result, Exception exception, long duration) {
        if (exception == null) {
            logger.info("method={},name={},tag={},args={},duration={},result={}",
                    point.getMethod().getName(), point.getName(), point.getTag(),
                    Json.toJson(point.getArgs()), duration, Json.toJson(result));
        } else {
            if (exception instanceof IException) {
                logger.error("method={},name={},tag={},args={},duration={}",
                        point.getMethod().getName(), point.getName(), point.getTag(),
                        Json.toJson(point.getArgs()), duration);
            } else {
                logger.error("method={},name={},tag={},args={},duration={},exception={}",
                        point.getMethod().getName(), point.getName(), point.getTag(),
                        Json.toJson(point.getArgs()), duration, exception);
            }
        }
    }
}
