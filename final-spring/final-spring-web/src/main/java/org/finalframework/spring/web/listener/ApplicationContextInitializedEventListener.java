package org.finalframework.spring.web.listener;

import org.finalframework.coding.spring.ApplicationEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationContextInitializedEvent;
import org.springframework.context.ApplicationListener;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-09 21:18:06
 * @since 1.0
 */
@ApplicationEventListener
public class ApplicationContextInitializedEventListener implements ApplicationListener<ApplicationContextInitializedEvent> {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationContextInitializedEventListener.class);
    @Override
    public void onApplicationEvent(ApplicationContextInitializedEvent event) {
        System.out.println(event.getClass().getCanonicalName());
    }
}
