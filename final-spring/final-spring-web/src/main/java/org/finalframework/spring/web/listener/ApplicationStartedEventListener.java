package org.finalframework.spring.web.listener;

import org.finalframework.coding.spring.ApplicationEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-09 21:18:06
 * @since 1.0
 */
@ApplicationEventListener
public class ApplicationStartedEventListener implements ApplicationListener<ApplicationStartedEvent> {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationStartedEventListener.class);

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        logger.info("Application Started!!!");
    }
}
