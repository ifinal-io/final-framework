package org.ifinal.finalframework.auto.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.annotation.processing.Processor;

/**
 * AutoProcessor.
 *
 * @author likly
 * @version 1.0.0
 * @see Processor
 * @since 1.0.0
 */
@AutoService(value = Processor.class, ignore = true)
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface AutoProcessor {

}
