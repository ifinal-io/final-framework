package org.ifinal.finalframework.context.listener;

import lombok.extern.slf4j.Slf4j;
import org.ifinal.auto.spring.factory.annotation.SpringApplicationListener;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
@SpringApplicationListener
public class ApplicationEnvironmentPreparedEventListener implements
    ApplicationListener<ApplicationEnvironmentPreparedEvent> {

    @Override
    public void onApplicationEvent(final ApplicationEnvironmentPreparedEvent event) {

        logger.info(event.getClass().getCanonicalName());
    }

}
