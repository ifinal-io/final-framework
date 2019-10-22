package org.finalframework.spring.web.listener;

import org.finalframework.spring.coding.ApplicationEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationListener;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-09 21:18:06
 * @since 1.0
 */
@ApplicationEventListener
public class ApplicationStartingEventListener implements ApplicationListener<ApplicationStartingEvent> {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationStartingEventListener.class);

    @Override
    public void onApplicationEvent(ApplicationStartingEvent event) {
        System.out.println(event.getClass().getCanonicalName());
    }
}
