package org.finalframework.spring.aop;

/**
 * AOP 切点操作描述
 *
 * @author likly
 * @version 1.0
 * @date 2019-03-26 09:40:18
 * @since 1.0
 */
public interface Operation {

    /**
     * 切点名称
     */
    String name();

    /**
     * 调用者
     */
    Class<? extends Invocation> invocation();
}
