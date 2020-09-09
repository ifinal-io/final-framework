

package org.finalframework.spring.aop;

import org.springframework.core.Ordered;
import org.springframework.lang.NonNull;

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
    @NonNull
    String name();

    default int order() {
        return Ordered.LOWEST_PRECEDENCE;
    }

    /**
     * 调用者
     */
    @NonNull
    Class<? extends OperationHandler> handler();

    @NonNull
    Class<? extends Executor> executor();

}
