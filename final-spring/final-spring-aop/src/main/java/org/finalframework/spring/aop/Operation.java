package org.finalframework.spring.aop;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-26 09:40:18
 * @since 1.0
 */
public interface Operation {

    String name();

    Class<? extends Invocation> invocation();
}
