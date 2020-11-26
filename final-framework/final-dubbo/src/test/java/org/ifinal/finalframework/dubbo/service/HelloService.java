package org.ifinal.finalframework.dubbo.service;

import org.springframework.lang.NonNull;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface HelloService {

    String hello(@NonNull String who);
}
