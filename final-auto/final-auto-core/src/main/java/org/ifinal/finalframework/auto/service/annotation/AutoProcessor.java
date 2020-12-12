package org.ifinal.finalframework.auto.service.annotation;

import javax.annotation.processing.Processor;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * @author likly
 * @version 1.0.0
 * @see Processor
 * @since 1.0.0
 */
@AutoService(Processor.class)
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface AutoProcessor {

}
