package org.ifinal.finalframework.dubbo.service;

import org.springframework.lang.NonNull;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class HelloServiceImpl implements HelloService {
    @Override
    public String hello(@NonNull String who) {
        return String.format("hello %s!", who);
    }
}
