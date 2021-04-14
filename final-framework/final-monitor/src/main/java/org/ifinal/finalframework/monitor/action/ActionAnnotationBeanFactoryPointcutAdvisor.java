package org.ifinal.finalframework.monitor.action;

import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import org.ifinal.finalframework.aop.AnnotationAttributesAnnotationBuilder;
import org.ifinal.finalframework.aop.single.SingleAnnotationBeanFactoryPointcutAdvisor;
import org.ifinal.finalframework.monitor.annotation.ActionMonitor;
import org.ifinal.finalframework.monitor.handler.ActionInterceptorHandler;

import java.util.Arrays;

import javax.annotation.Resource;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
@SuppressWarnings("serial")
public class ActionAnnotationBeanFactoryPointcutAdvisor extends
    SingleAnnotationBeanFactoryPointcutAdvisor<ActionMonitor, AnnotationAttributes, Recorder> {

    @Resource
    private Recorder recorder;

    public ActionAnnotationBeanFactoryPointcutAdvisor() {
        super(ActionMonitor.class, new AnnotationAttributesAnnotationBuilder<>(),
            Arrays.asList(new ActionInterceptorHandler()));
    }

    @Override
    @NonNull
    protected Recorder getExecutor(final AnnotationAttributes annotation) {

        return recorder;
    }

}
