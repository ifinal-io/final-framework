package org.finalframework.spring.web.listener;

import org.finalframework.coding.spring.ApplicationEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-09 21:18:06
 * @since 1.0
 */
@ApplicationEventListener
public class ApplicationEnvironmentPreparedEventListener implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationEnvironmentPreparedEventListener.class);

    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
        System.out.println(event.getClass().getCanonicalName());
    }
}
