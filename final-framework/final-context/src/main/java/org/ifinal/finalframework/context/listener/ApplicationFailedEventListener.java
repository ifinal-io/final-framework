package org.ifinal.finalframework.context.listener;

import org.ifinal.finalframework.auto.spring.factory.annotation.SpringApplicationListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationFailedEvent;
import org.springframework.context.ApplicationListener;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@SpringApplicationListener
public class ApplicationFailedEventListener implements ApplicationListener<ApplicationFailedEvent> {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationFailedEventListener.class);

    @Override
    public void onApplicationEvent(ApplicationFailedEvent event) {
        logger.error("Application run failed!", event.getException());
    }
}
