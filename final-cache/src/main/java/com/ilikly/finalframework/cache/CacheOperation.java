package com.ilikly.finalframework.cache;

import java.lang.annotation.Annotation;
import java.util.concurrent.TimeUnit;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-22 16:12:47
 * @since 1.0
 */
public interface CacheOperation<A extends Annotation> {

    String keyPattern();

    String[] keys();

    String fieldPattern();

    String[] fields();

    String condition();

    String expire();

    Long ttl();

    TimeUnit timeUnit();
}
