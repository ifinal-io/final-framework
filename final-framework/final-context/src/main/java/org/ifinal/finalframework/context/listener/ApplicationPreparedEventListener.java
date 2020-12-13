package org.ifinal.finalframework.context.listener;

import lombok.extern.slf4j.Slf4j;
import org.ifinal.finalframework.auto.spring.factory.annotation.SpringApplicationListener;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.context.ApplicationListener;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
@SpringApplicationListener
public class ApplicationPreparedEventListener implements ApplicationListener<ApplicationPreparedEvent> {

    @Override
    public void onApplicationEvent(final ApplicationPreparedEvent event) {

        logger.info(event.getClass().getCanonicalName());
    }

}
