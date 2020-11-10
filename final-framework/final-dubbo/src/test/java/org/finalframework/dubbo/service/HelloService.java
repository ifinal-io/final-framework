package org.finalframework.dubbo.service;

import org.springframework.lang.NonNull;

/**
 * @author likly
 * @version 1.0
 * @date 2020-08-27 10:37:33
 * @since 1.0
 */
public interface HelloService {

    String hello(@NonNull String who);
}
