

package org.finalframework.data.trigger.interceptor;


import org.finalframework.data.service.AbsService;
import org.finalframework.data.trigger.annotation.TriggerPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.core.annotation.AnnotatedElementUtils;

import java.lang.reflect.Method;

/**
 * @author likly
 * @version 1.0
 * @date 2020-04-03 14:20:00
 * @since 1.0
 */
public class TriggerPointcut extends StaticMethodMatcherPointcut {

    private static final Logger logger = LoggerFactory.getLogger(TriggerPointcut.class);

    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        final boolean matches = AnnotatedElementUtils.hasAnnotation(method, TriggerPoint.class)
                && !AbsService.class.isAssignableFrom(targetClass);
        if (matches) {
            logger.info("find trigger point: #{}", method.getName());
        }
        return matches;
    }
}

