package org.finalframework.auto.spring.factory;

import org.finalframework.auto.spring.factory.annotation.SpringApplicationListener;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

/**
 * @author likly
 * @version 1.0
 * @date 2020/9/4 14:31:09
 * @since 1.0
 */
@SpringApplicationListener
public class MyApplicationListener implements ApplicationListener<ApplicationReadyEvent> {
    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {

    }
}
