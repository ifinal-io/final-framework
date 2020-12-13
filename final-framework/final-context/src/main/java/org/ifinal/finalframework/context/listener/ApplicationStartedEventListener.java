package org.ifinal.finalframework.context.listener;

import org.ifinal.finalframework.auto.spring.factory.annotation.SpringApplicationListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@SpringApplicationListener
public class ApplicationStartedEventListener implements ApplicationListener<ApplicationStartedEvent> {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationStartedEventListener.class);

    @Override
    public void onApplicationEvent(final ApplicationStartedEvent event) {

        logger.info("Application Started!!!");
    }

}
