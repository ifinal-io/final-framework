package org.ifinal.finalframework.aop;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.core.annotation.AnnotationAttributes;

import java.util.Collection;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface AnnotationMethodInvocation extends MethodInvocation {

    Collection<AnnotationAttributes> getAnnotations();

}
