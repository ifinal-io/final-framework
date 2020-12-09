package org.ifinal.finalframework.monitor.trace;

import org.ifinal.finalframework.aop.simple.SimpleAnnotationBeanFactoryPointAdvisor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
//@Component
public class TraceBeanFactoryPointcutAdvisor extends SimpleAnnotationBeanFactoryPointAdvisor<Tracer> {

    @Resource
    private Tracer tracer;

    public TraceBeanFactoryPointcutAdvisor() {
        super(Arrays.asList(Service.class, ResponseBody.class), Arrays.asList(new TraceLoggerInterceptorHandler()));
    }

    @Override
    protected Tracer getExecutor() {
        return tracer;
    }
}
