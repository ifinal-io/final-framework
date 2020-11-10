package org.finalframework.dubbo.service;

import org.springframework.lang.NonNull;

/**
 * @author likly
 * @version 1.0
 * @date 2020-08-27 10:38:19
 * @since 1.0
 */
public class HelloServiceImpl implements HelloService {
    @Override
    public String hello(@NonNull String who) {
        return String.format("hello %s!", who);
    }
}
