package org.finalframework.spring.web.listener;

import lombok.extern.slf4j.Slf4j;
import org.finalframework.spring.coding.ApplicationEventListener;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-09 21:18:06
 * @since 1.0
 */
@Slf4j
@ApplicationEventListener
public class ApplicationStartedEventListener implements ApplicationListener<ApplicationStartedEvent> {

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        logger.info("Application Started!!!");
    }
}
