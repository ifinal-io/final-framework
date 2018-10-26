package cn.com.likly.finalframework.spring.web.logger;

import cn.com.likly.finalframework.data.json.Json;
import cn.com.likly.finalframework.spring.monitor.MethodMonitorAspect;
import cn.com.likly.finalframework.spring.monitor.MethodPoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-09 13:15
 * @since 1.0
 */
@Slf4j
@Component
public class MethodMonitorLogger implements MethodMonitorAspect.MethodMonitorListener {

    @Resource
    private MethodMonitorAspect methodMonitorAspect;

    @PostConstruct
    public void init() {
        methodMonitorAspect.registerMethodMonitorListener(this);
    }

    @Override
    public void onFinish(MethodPoint point, Object result, Exception exception, Long duration) {
        if (exception == null) {
            logger.debug("method={},name={},tag={},args={},duration={},result={}",
                    point.getMethod().getName(), point.getName(), point.getTag(),
                    Json.toJson(point.getArgs()), duration, Json.toJson(result));
        } else {
            logger.error("method={},name={},tag={},args={},duration={},exception={}",
                    point.getMethod().getName(), point.getName(), point.getTag(),
                    Json.toJson(point.getArgs()), duration, exception);
        }
    }
}
