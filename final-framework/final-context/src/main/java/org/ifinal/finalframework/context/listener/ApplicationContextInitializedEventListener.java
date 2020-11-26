package org.ifinal.finalframework.context.listener;

import lombok.extern.slf4j.Slf4j;
import org.ifinal.finalframework.auto.spring.factory.annotation.SpringApplicationListener;
import org.springframework.boot.context.event.ApplicationContextInitializedEvent;
import org.springframework.context.ApplicationListener;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
@SpringApplicationListener
public class ApplicationContextInitializedEventListener implements ApplicationListener<ApplicationContextInitializedEvent> {

    @Override
    public void onApplicationEvent(ApplicationContextInitializedEvent event) {
        logger.info(event.getClass().getCanonicalName());
    }
}
